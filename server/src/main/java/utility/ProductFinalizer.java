package utility;

import data.Product;

import java.time.ZonedDateTime;

public class ProductFinalizer {
    public static void finalize(Product product){
        product.setId(IdManager.getNextProductId());
        product.setCreationDate(ZonedDateTime.now());
        if(product.getManufacturer() != null && product.getManufacturer().getId() == null){
            product.getManufacturer().setId(IdManager.getNextOrgId());
        }
    }
}
