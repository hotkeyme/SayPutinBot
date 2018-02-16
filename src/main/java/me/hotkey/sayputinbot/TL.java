package me.hotkey.sayputinbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author Silver
 */
public class TL extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "SayPutinBot";
        //возвращаем юзера
    }

    @Override
    public String getBotToken() {
        return "455984549:AAGeVbJ_TIWaCZrDgfygGCJHklILMmhmsdA";
        //Токен бота
    }

    public static void init() {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new TL());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void onUpdateReceived(Update e) {
        String txtMsg = null;
        Message msg = e.getMessage(); // Это нам понадобится
        Message pst = e.getChannelPost();
//        Message msgg = e.GET
        if (msg == null) {
            msg = pst;
        };

        String txt = msg.getText().toUpperCase();
        ParamXML param1 = new ParamXML();
        boolean ifNotAnswer = true;

        for (String nic : param1.nic) {
            if (txt.contains(nic.toUpperCase())) {
                Random rand = new Random();
                int randomNum = rand.nextInt(param1.coup.length);
                sendMsg(msg, param1.coup[randomNum]);
                ifNotAnswer = false;
            }
        }

        if (e.getMessage() != null && e.getMessage().getChatId() > 0 && ifNotAnswer) {
            switch (txt) {
                case "/START":
                    String FirstName = msg.getFrom().getFirstName();
                    sendMsg(msg, "Привет, " + FirstName + "!\nЭтот бот ничего не умеет кроме неуместного выкрикивания часчтушек про В.В. Путина.\nсорян");
                    break;
                case "/PHONE":

                case "/PARAM":
                    sendMsg(msg, "PARAM");
                default:
                    txtMsg = "моя твоя не понимай :( сорянчик!";
                    sendMsg(msg, txtMsg);
                    break;
            }
            /*            if (txt.equals("/START")) {
                String FirstName = msg.getFrom().getFirstName();
                sendMsg(msg, "Привет, " + FirstName + "!\nЭтот бот ничего не умеет кроме неуместного выкрикивания часчтушек про В.В. Путина.\nсорян");
            } else {
                txtMsg = "моя твоя не понимай :( сорянчик!";
                sendMsg(msg, txtMsg);
            }
             */
        }

    }

//    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        System.out.println("ChatId = " + msg.getChatId().toString());
        s.setText(text);
        s.setReplyToMessageId(msg.getMessageId());

        SendMessage sc = new SendMessage();
        sc.setChatId("114430755");
        sc.setText("ChatId = " + msg.getChatId().toString() + "\n"
                + "UserName = @" + msg.getFrom().getUserName() + "\n"
                + "Text = " + msg.getText()
        );

        // create keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        s.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // new list
        List<KeyboardRow> keyboard = new ArrayList<>();

        // first keyboard line
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Share phone >").setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        // add array to list
        keyboard.add(keyboardFirstRow);

        // add list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);

        try { //Чтобы не крашнулась программа при вылете Exception 
            execute(s);
            execute(sc);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
