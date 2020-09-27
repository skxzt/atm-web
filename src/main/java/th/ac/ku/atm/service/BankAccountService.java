package th.ac.ku.atm.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {

    private RestTemplate restTemplate;

    public BankAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BankAccount> getCustomerBankAccount(int customerId) {
        String url = "http://localhost:8091/api/bankaccount/customer/" + customerId;
        ResponseEntity<BankAccount[]> response = restTemplate.getForEntity(url, BankAccount[].class);
        BankAccount[] accounts = response.getBody();
        return Arrays.asList(accounts);
    }

    public void createBankAccount(BankAccount bankAccount){
        String url = "http://localhost:8091/api/bankaccount";
        restTemplate.postForObject(url, bankAccount, BankAccount.class);
    }

    public List<BankAccount> getBankAccounts() {
        String url = "http://localhost:8091/api/bankaccount";
        ResponseEntity<BankAccount[]> response = restTemplate.getForEntity(url, BankAccount[].class);
        BankAccount[] accounts = response.getBody();
        return Arrays.asList(accounts);
    }

    public BankAccount getBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;
        ResponseEntity<BankAccount> response = restTemplate.getForEntity(url, BankAccount.class);
        return response.getBody();
    }

    public void withdrawBankAccount(BankAccount bankAccount, double wValue) {
        if(bankAccount.getBalance() >= wValue){
            String url = "http://localhost:8091/api/bankaccount/" + bankAccount.getId();
            double newBalance = bankAccount.getBalance() - wValue;
            restTemplate.put(url, newBalance);
        }
    }

    public void depositBankAccount(BankAccount bankAccount, double dValue) {
        String url = "http://localhost:8091/api/bankaccount/" + bankAccount.getId();
        double newBalance = bankAccount.getBalance() + dValue;
        restTemplate.put(url, newBalance);
    }

    public void deleteBankAccount(BankAccount bankAccount){
        String url = "http://localhost:8091/api/bankaccount/" + bankAccount.getId();
        restTemplate.delete(url, bankAccount);
    }

}
