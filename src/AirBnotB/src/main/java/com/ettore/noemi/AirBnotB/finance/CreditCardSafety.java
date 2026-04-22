/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.finance;

import com.ettore.noemi.AirBnotB.DTOs.creditCardDTO;

/**
 *
 * @author ettore
 */
public class CreditCardSafety {
    public static boolean luhnAlgo(creditCardDTO _card) {
        String ccNum = String.valueOf(_card.getNumber());
        int sum = 0;
        boolean doubleDigit = false;

        for (int i = ccNum.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(ccNum.charAt(i));
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }

        return sum % 10 == 0;
    }

}
