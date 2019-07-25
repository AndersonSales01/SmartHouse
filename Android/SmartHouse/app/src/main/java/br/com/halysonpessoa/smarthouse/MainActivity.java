package br.com.halysonpessoa.smarthouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {

    static String MQTTHOST = "tcp://postman.cloudmqtt.com:10261";
    static String USERNAME = "rxixspfh";
    static String PASSWORD = "ipLU7FeuLSz8";
    String topicStr = "LED";
    MqttAndroidClient client;

    Switch simpleSwitchLampada;
    Switch switchTomada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this,"conectado",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this,"não conectado",Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void inicializar(){
        simpleSwitchLampada = (Switch) findViewById(R.id.switchLampada);
        switchTomada = (Switch) findViewById(R.id.switchTomada);
    }

    public void lampada(View v){
        Boolean switchState = simpleSwitchLampada.isChecked();
        if(switchState){
            ligar1(v);
        }else{
            desligar1(v);
        }
    }

    public void tomada(View v){
        Boolean switchState = switchTomada.isChecked();
        if(switchState){
            ligarTomada(v);
        }else{
            desligarTomada(v);
        }
    }

    public void ligar1(View v){
        String topic = topicStr;
        String message = "L1";
        try {
            client.publish(topic, message.getBytes(),0,false    );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void desligar1(View v){
        String topic = topicStr;
        String message = "D1";
        try {
            client.publish(topic, message.getBytes(),0,false    );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void ligarTomada(View v){
        String topic = topicStr;
        String message = "L2";
        try {
            client.publish(topic, message.getBytes(),0,false    );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void desligarTomada(View v) {
        String topic = topicStr;
        String message = "D2";
        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
