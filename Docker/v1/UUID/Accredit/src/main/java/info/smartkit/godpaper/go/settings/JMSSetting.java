package info.smartkit.godpaper.go.settings;

import info.smartkit.godpaper.go.activemq.ActivemqVariables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "jms")
public class JMSSetting
{

    private static Logger LOG = LogManager.getLogger(JMSSetting.class);

    private String brokerUrl;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
        //
        ActivemqVariables.channelName = channelName;
        LOG.info("channelName:" + ActivemqVariables.channelName);
    }

    private String channelName;

    public String getBrokerUrl()
    {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl)
    {
        this.brokerUrl = brokerUrl;
        //
        ActivemqVariables.brokerUrl = brokerUrl;
        LOG.info("brokerUrl:" + ActivemqVariables.brokerUrl);
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
        //
        ActivemqVariables.queueName = queueName;
        LOG.info("queueName:" + ActivemqVariables.queueName);
    }

    private String queueName;

}
