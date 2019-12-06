还在类中写广播监听网络的变化？
  本库帮助你简化这些操作，在需要监听的方法上面添加注解即可。
  ```java
  public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = NetWorkStatusManager.register(this);
    }


    @NetWorkType(NetWork.NETWORK_2G)
    public void Test(NetWork netWork)
    {
        Log.i(TAG, "Test: " + netWork.name());
    }

    @NetWorkType(NetWork.NETWORK_WIFI)
    public void TestWifi(NetWork netWork)
    {
        Log.i(TAG, "Test: " + netWork.name());
    }

    @NetWorkType(NetWork.NETWORK_NO)
    public void TestWifiNO(NetWork netWork)
    {
        Log.i(TAG, "Test: " + netWork.name());
    }

    @Override
    protected void onDestroy()
    {
        register.unregister();
        super.onDestroy();
    }
}
  ```
