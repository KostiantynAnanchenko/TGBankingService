package Marks;

import com.example.TGBankingService.bankDataReader.currencyParser.markingSymbolyc.PutMarks;
import com.example.TGBankingService.bankDataReader.db.DataBase;
import com.example.TGBankingService.bankDataReader.dto.UsersDTO;
import com.example.TGBankingService.bankDataReader.enums.BanksName;

import org.junit.Test;

import java.util.List;

public class PutMarksTest {
    @Test
    public void putFuncTest(){
        try (DataBase db = DataBase.getInstance()){
            UsersDTO userInfo = db.getUser(0);

            List<String> banks = userInfo.getBank();
            PutMarks<BanksName> putClass = new PutMarks<>();
            putClass.addButtons(List.of(BanksName.PRIVATBANK,
                    BanksName.MONOBANK,
                    BanksName.OSHCHADBANK), banks);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
