import com.google.gson.Gson;
import com.google.gson.reflect.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.FileReader;
import java.util.List;
import java.lang.reflect.*;

import java.util.ArrayList;
import java.util.List;

public class TGKeyboards {
    private class Keyboard{
        public String[] buttons;
        public boolean vertical;

    }
    public static ArrayList<ReplyKeyboardMarkup> replyKeyboardMarkups = new ArrayList<>();
    public static ArrayList<InlineKeyboardMarkup> inlineKeyboardMarkups = new ArrayList<>();
    private static List<Keyboard> keyboards;

    public TGKeyboards(){
        iniKeyboards();
    }

    private  InlineKeyboardMarkup buildInlineKeyboard(){
        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton("1");
        button1.setCallbackData("1");
        InlineKeyboardButton button2 = new InlineKeyboardButton("2");
        button2.setCallbackData("2");

        row1.add(button1);
        row1.add(button2);
        rows.add(row1);
        inline.setKeyboard(rows);
        return inline;
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
            Type listType = new TypeToken<List<Keyboard>>(){}.getType();
            keyboards = gson.fromJson(reader, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void iniKeyboards(){
        parseKeyboards();
        inlineKeyboardMarkups.add(buildInlineKeyboard());

        for(Keyboard k : keyboards){
            replyKeyboardMarkups.add(buildKeyboard(k));
        }
    }
}

