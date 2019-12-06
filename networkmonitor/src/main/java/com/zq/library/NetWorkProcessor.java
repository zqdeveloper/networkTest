package com.zq.library;

import com.google.auto.service.AutoService;
import com.zq.annotations.NetWorkType;
import com.zq.type.NetWork;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

/**
 * @author 张青
 */
//@AutoService(Processor.class)
@SupportedAnnotationTypes("com.zq.annotations.NetWorkType")
public class NetWorkProcessor extends AbstractProcessor
{
    public static final String SUFX = "$NetWork";
    Map<TypeElement, NetWorkPoint> map = new HashMap<TypeElement, NetWorkPoint>();
    private static final String PRIVATE = "private";
    private static final String PROTECTED = "protected";
    private static final String STATIC = "static";
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment)
    {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment)
    {
        map.clear();
        for (TypeElement typeElement : set)
        {
            for (Element element : roundEnvironment.getElementsAnnotatedWith(typeElement))
            {
                Set<Modifier> modifiers = element.getModifiers();
                if (modifiers.contains(PRIVATE) || modifiers.contains(PROTECTED) || modifiers.contains(STATIC))
                {
                    continue;
                }
                addElementToGenerateFile(element);
            }
        }
        for (Map.Entry<TypeElement, NetWorkPoint> entry : map.entrySet())
        {
            NetWorkPoint value = entry.getValue();
            TypeElement key = entry.getKey();
            try
            {
                JavaFileObject javaFileObject = filer.createSourceFile(value.getFqcn(), key);
                Writer writer = javaFileObject.openWriter();
                writer.write(value.brewJava());
                writer.flush();
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.RELEASE_7;
    }

    private void addElementToGenerateFile(Element element)
    {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        ExecutableElement executableElement = (ExecutableElement) element;
        List<? extends VariableElement> parameters = executableElement.getParameters();
        if (parameters.isEmpty() || parameters.size() > 1)
        {
            return;
        }
        String methondName = executableElement.getSimpleName().toString();
        NetWorkType annotation = executableElement.getAnnotation(NetWorkType.class);
        if (annotation == null)
        {
            return;
        }
        NetWork netWork = annotation.value();
        String qualifiedName = typeElement.getQualifiedName().toString();
        int index = qualifiedName.lastIndexOf(".");
        String packageName = qualifiedName.substring(0, index);
        String className = qualifiedName.substring(index + 1)+SUFX;
        NetWorkPoint netWorkPoint = map.get(typeElement);
        if (netWorkPoint == null)
        {
            netWorkPoint = new NetWorkPoint();
            netWorkPoint.className = className;
            netWorkPoint.targetClass=typeElement.getQualifiedName().toString();
            netWorkPoint.packageName = packageName;
            netWorkPoint.addMethod(methondName, netWork);
            map.put(typeElement, netWorkPoint);
        }
        else
        {
            netWorkPoint.addMethod(methondName, netWork);
        }
    }
}
