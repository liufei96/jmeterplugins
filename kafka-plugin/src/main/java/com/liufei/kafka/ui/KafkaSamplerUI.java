package com.liufei.kafka.ui;

import com.liufei.kafka.KafkaSampler;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("deprecation")
public class KafkaSamplerUI extends AbstractSamplerGui {

    private static final Logger log = LoggingManager.getLoggerForClass();

    private final JLabeledTextField brokersField = new JLabeledTextField("Brokers");
    private final JLabeledTextField topicField = new JLabeledTextField("Topic");

    private final JLabeledTextField messageSerializerField = new JLabeledTextField("Message Serializer");
    private final JLabeledTextField keySerializerField = new JLabeledTextField("Key Serializer");

    private final JLabel textArea = new JLabel("Message");
    private final JSyntaxTextArea textMessage = new JSyntaxTextArea(10, 50);
    private final JTextScrollPane textPanel = new JTextScrollPane(textMessage);

    public KafkaSamplerUI() {
        this.init();
    }


    private void init() {
        log.info("Initializing kafka UI...");
        setLayout(new BorderLayout());
        setBorder(makeBorder());

        add(makeTitlePanel(), BorderLayout.NORTH);
        // 垂直面板
        JPanel mainPanel = new VerticalPanel();
        add(mainPanel, BorderLayout.CENTER);

        JPanel DPanel = new JPanel();
        DPanel.setLayout(new GridLayout(3, 2));
        DPanel.add(brokersField);
        DPanel.add(topicField);
        //DPanel.add(keyField);
        DPanel.add(messageSerializerField);
        DPanel.add(keySerializerField);

        JPanel ControlPanel = new VerticalPanel();
        ControlPanel.add(DPanel);
        ControlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Parameters"));
        mainPanel.add(ControlPanel);

        JPanel ContentPanel = new VerticalPanel();
        JPanel messageContentPanel = new JPanel(new BorderLayout());
        messageContentPanel.add(this.textArea, BorderLayout.NORTH);
        messageContentPanel.add(this.textPanel, BorderLayout.CENTER);
        ContentPanel.add(messageContentPanel);
        ContentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Content"));
        mainPanel.add(ContentPanel);
    }


    public String getLabelResource() {
        throw new IllegalStateException("This shouldn't be called");
    }

    public TestElement createTestElement() {
        // 该方法创建一个新的Sampler，然后将界面中的数据设置到这个新的Sampler实例中。
        KafkaSampler sampler = new KafkaSampler();
        this.setupSamplerProperties(sampler);
        return sampler;
    }

    @Override
    public void configure(TestElement element) {
        // 该方法用于把Sampler中的数据加载到界面中。在实现自己的逻辑之前，先调用一下父类的方法super.configure(el)，这样可以确保框架自动为你加载一些缺省数据，比如Sampler的名字。
        super.configure(element);
    }

    public void modifyTestElement(TestElement testElement) {
        // 这个方法用于把界面的数据移到Sampler中，刚好与上面的方法相反。在调用自己的实现方法之前，请先调用一下super.configureTestElement(e)，这个会帮助移到一些缺省的数据。
    }

    @Override
    public void clearGui() {
        // 该方法会在reset新界面的时候调用，这里可以填入界面控件中需要显示的一些缺省的值。
        super.clearGui();
    }

    private void setupSamplerProperties(KafkaSampler sampler) {
        this.configureTestElement(sampler);
        sampler.setBrokers(this.brokersField.getText());
        sampler.setTopic(this.topicField.getText());
        //sampler.setKey(this.keyField.getText());
        sampler.setMessage(this.textMessage.getText());
        sampler.setMessageSerializer(this.messageSerializerField.getText());
        sampler.setKeySerializer(this.keySerializerField.getText());
    }

    @Override
    public String getStaticLabel() {
        return "Kafka Sampler";
    }
}
