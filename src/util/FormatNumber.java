/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Luxury
 */
public class FormatNumber {

    public static String formatNumber(int number) {
        NumberFormat formatter = new DecimalFormat("###,###");
        String resp = formatter.format(number);
        resp = resp.replaceAll(",", ".");
        return resp;

    }
}
