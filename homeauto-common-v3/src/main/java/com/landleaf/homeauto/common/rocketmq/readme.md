##rocketmq配置说明：
homeauto:
  rocketmq:
    producer:
     #开启生产者配置
      enable: true 
      groupName: household
      namesrvAddr: 47.102.46.249:9876
      maxMessageSize: 4096
      sendMsgTimeout: 30000
     #重发次数
      retryTimesWhenSendFailed: 3
    consumer:
    #开启消费者配置
      enable: true  
     #消费组名称请用唯一，默认集群方式会以平均消费方式负载均衡
      groupName: household 
      namesrvAddr: 47.102.46.249:9876
     #topic-tags;topic-tags 多个用";"分隔;topic与tag用"-"分隔
      topics: household-tags    
      consumeThreadMin: 16
      consumeThreadMax: 32
     #每次获取最大数
      consumeMessageBatchMaxSize: 10
  
    
    
    
    
 ###消费者使用说明 
 @RocketMQConsumeService(topic = TopicEnum.DEMO_TOPIC, tags = {TagConstants.TAGS_DEFAULT})
 public class DemoConsumerMsgProcess extends AbstractMQMsgProcessor {
 
     private static final Logger LOGGER = LoggerFactory.getLogger(DemoConsumerMsgProcess.class);
 
 
     @Override
     protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt messageExt) {
         try {
             String msgBody = new String(messageExt.getBody(), "utf-8");
             System.out.println(msgBody);
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         MQConsumeResult result = new MQConsumeResult();
         result.setSuccess(true);
         return result;
     }
 
 }
