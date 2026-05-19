package com.hypers.hm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.RandomAccessFile;
import com.hypers.hm.debug.ADB;

public class TerminalActivity extends AppCompatActivity {

    private TextView textviewTerminal;
    private ScrollView scrollView;
    private ImageView btnClose;

    private ADB adb;
    private boolean isListeningLog = true;
    private long lastReadPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter_activity);

        textviewTerminal = findViewById(R.id.textview_terminal);
        scrollView = findViewById(R.id.scroll_view);
        btnClose = findViewById(R.id.btn_close);

        adb = ADB.getInstance(getApplicationContext());

        
        btnClose.setOnClickListener(v -> finish());       
        startLiveLogStream();
    }

    private void startLiveLogStream() {
        new Thread(() -> {
            File bufferFile = adb.outputBufferFile;
            
            lastReadPosition = 0; 

            while (isListeningLog) {
                try {
                    Thread.sleep(100);
                    
                    if (bufferFile != null && bufferFile.exists()) {
                        long currentLength = bufferFile.length();
                        
                        if (currentLength > lastReadPosition) {
                            RandomAccessFile raf = new RandomAccessFile(bufferFile, "r");
                            raf.seek(lastReadPosition);
                            
                            byte[] newBytes = new byte[(int) (currentLength - lastReadPosition)];
                            raf.readFully(newBytes);
                            raf.close();
                            
                            String incomingLog = new String(newBytes);
                            lastReadPosition = currentLength;

                            runOnUiThread(() -> {
                                textviewTerminal.append(incomingLog);
                                textviewTerminal.invalidate();
                                scrollToBottom();
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void scrollToBottom() {
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isListeningLog = false; 
    }
}
