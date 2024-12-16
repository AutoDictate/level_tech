package com.level.tech.config;

import com.level.tech.entity.AccountType;
import com.level.tech.entity.Bank;
import com.level.tech.entity.Category;
import com.level.tech.entity.State;
import com.level.tech.repository.AccountTypeRepository;
import com.level.tech.repository.BankRepository;
import com.level.tech.repository.CategoryRepository;
import com.level.tech.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MasterData {

    private final AccountTypeRepository accountTypeRepository;

    private final CategoryRepository categoryRepository;

    private final StateRepository stateRepository;

    private final BankRepository bankRepository;

    public void loadBanks() {
        List<String> banks = List.of(
                "Allahabad Bank",
                "Andhra Bank",
                "Axis Bank",
                "Bank of Bahrin and Kuwait",
                "Bank of Baroda - Corporate Banking",
                "Bank of Baroda - Retail Banking",
                "Bank of India",
                "Bank of Maharashtra",
                "Canara Bank",
                "Central Bank of India",
                "City Union Bank",
                "Corporation Bank",
                "Deutsche Bank",
                "Development Credit Bank",
                "Dhanlaxmi Bank",
                "Fedral Bank",
                "HDFC Bank",
                "ICICI Bank",
                "IDBI Bank",
                "Indian Bank",
                "Indian Overseas Bank",
                "Indusland Bank",
                "ING Vysya Bank",
                "Jammu and Kashmir Bank",
                "Karnataka Bank Ltd",
                "Karur Vysya Bank",
                "Kotak Bank",
                "Laxmi Vilas Bank",
                "Oriental Bank of Commerce",
                "Punjab National Bank - Corporate Banking",
                "Punjab National Bank - Retail Banking",
                "Punjab & Sind Bank",
                "Shamrao Vithal Co-operative Bank",
                "South Indian Bank",
                "State Bank of Bikaner & Jaipur",
                "State Bank of Hyderabad",
                "State Bank of India",
                "State Bank of Mysore",
                "State Bank of Patiala",
                "State Bank of Travancore",
                "Syndicate Bank",
                "Tamilnadu Mercantile Bank Ltd",
                "UCO Bank",
                "Union Bank of India",
                "United Bank of India",
                "Vijaya Bank",
                "Yes Bank Ltd"
        );

        for (String bank : banks) {
            Bank b = new Bank();
            b.setName(bank);
            bankRepository.save(b);
        }
    }

    public void loadStates() {
        List<String> states = List.of(
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttar Pradesh",
                "Uttarakhand",
                "West Bengal"
        );

        for (String state : states) {
            State s = new State();
            s.setName(state);
            stateRepository.save(s);
        }
    }

    public void loadCategories() {
        List<String> categories = List.of(
                "ACCESSORIES",
                "PET SHOP",
                "RENTAL",
                "REPAIRING WORK",
                "SURVEY INSTRUMENTS",
                "SURVEY WORK",
                "TESTING EQUIPMENT (MACHINE & APPLIANCE)"
        );

        for (String category : categories) {
            Category cat = new Category(category);
            categoryRepository.save(cat);
        }
    }

    public void loadAccountType() {
        List<AccountType> accountTypes = List.of(
                new AccountType("CURRENT DEPOSITS/ACCOUNTS", LocalDateTime.now()),
                new AccountType("SAVING BANK/SAVING FUND DEPOSITS/ACCOUNTS", LocalDateTime.now()),
                new AccountType("RECURRING DEPOSITS/ACCOUNTS", LocalDateTime.now()),
                new AccountType("FIXED DEPOSITS/ACCOUNTS OR TERM DEPOSITS", LocalDateTime.now())
        );

        accountTypeRepository.saveAll(accountTypes);
    }

}
