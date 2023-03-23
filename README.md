# JUC
/*
* 用户购买商品下单成功后，系统会给用户发送各种消息提示用户『购买成功』
* 比如发送邮件、微信公众号消息、短信等。
* 所有的消息都发送成功后，在后台记录一条消息表示成功。
* 如果使用单线程操作也是可以的，但效率比较低
* 使用多线程的时候会遇到子线程消息还没发送完，主线程可能已经执行完，显示所有消息已发送的情况
* 这时候使用CountDownLatch就可以解决这种问题
* */