import com.google.gson.Gson;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.FileReader;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class TGKeyboards {
    private class Keyboard{
        public String[] buttons;
        public boolean vertical;

    }
    public static ArrayList<ReplyKeyboardMarkup> replyKeyboardMarkups = new ArrayList<>();
    private static List<Keyboard> keyboards;

    public TGKeyboards(){
        iniKeyboards();
    }

    private ReplyKeyboardMarkup buildKeyboard(Keyboard kb){
        ReplyKeyboardMarkup ans = new ReplyKeyboardMarkup();
        ans.setResizeKeyboard(true);
        ans.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrows = new ArrayList<>();
        if(kb.vertical){
            for(String i : kb.buttons){
                KeyboardRow keyrow = new KeyboardRow();
                keyrows.add(keyrow);
                keyrow.add(new KeyboardButton(i));
            }
        }
        else{
            KeyboardRow keyrow = new KeyboardRow();
            for(String i : kb.buttons){
                keyrow.add(new KeyboardButton(i));
            }
            keyrows.add(keyrow);
        }
        ans.setKeyboard(keyrows);
        return ans;
    }

    private void parseKeyboards(){
        Gson gson = new Gson();
        String filePath = getClass().getResource("Keyboards.json").toString().substring(5);
        try (FileReader reader = new FileReader(filePath)) {
            java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<List<Keyboard>>(){}.getType();
            keyboards = gson.fromJson(reader, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void iniKeyboards(){
        parseKeyboards();

        for(Keyboard k : keyboards){
            replyKeyboardMarkups.add(buildKeyboard(k));
        }
    }
}

