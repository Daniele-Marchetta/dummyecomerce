package it.ecommerce.util;

import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ecommerce.model.CartProduct;
import it.ecommerce.repository.PromotionRepository;

@Service
public class Util {
	@Autowired
	private PromotionRepository repoP;

	public static String formatString(String toformat) {
        if(toformat==null||toformat.length()==0) {
        	return "";
        }
		String s = toformat.toLowerCase();
		s = s.substring(0, 1).toUpperCase() + s.substring(1);
		return s;

	}

	public static double getTotalPayment(Iterable<CartProduct> cart) {
		double tot = 0;
		for (CartProduct c : cart) {
			tot = tot + (c.getPrice() * c.getQuantity());
		}
		return tot;
	}

	public static  double calcDiscount(double startPrice, Integer persDiscount, Integer genDiscount) {
		double finalPrice=startPrice;
		if(genDiscount!=null) {
			finalPrice=((100-genDiscount)*finalPrice)/100;
		}
		if(persDiscount!=null) {
			finalPrice=((100-persDiscount)*finalPrice)/100;
		}	
		return finalPrice;
	}
	
	public static String formatRole (String roleName) {
		String formatted = "ROLE_"+roleName.toUpperCase();
		return formatted;
	}
	
	public static BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) throws Exception {
	    return Scalr.resize(originalImage, targetWidth);
	}
}
