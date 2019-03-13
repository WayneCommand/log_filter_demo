# log_filter_demo

这是一个springboot web 记录接口请求和响应的DEMO

## 原理

用filter复制请求和响应的流，用来解决流读一次就失效了的问题。然后用复制出来的流去记录LOG
