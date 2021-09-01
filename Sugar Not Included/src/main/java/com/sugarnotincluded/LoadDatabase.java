package com.sugarnotincluded;

import com.sugarnotincluded.model.Beverage;
import com.sugarnotincluded.model.Customer;
import com.sugarnotincluded.model.Ingredient;
import com.sugarnotincluded.model.Recipe;
import com.sugarnotincluded.repository.BeverageRepository;
import com.sugarnotincluded.repository.CustomerRepository;
import com.sugarnotincluded.repository.IngredientRepository;
import com.sugarnotincluded.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Configuration
public class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDB(CustomerRepository customerRepository, IngredientRepository ingredientRepository,
                                    RecipeRepository recipeRepository, BeverageRepository beverageRepository) {
        return args -> {
            logger.info("*** Inserting some customers ***");
            logger.info("### Preloading " + customerRepository.save(new Customer("John Smith")));
            logger.info("### Preloading " + customerRepository.save(new Customer("William Smith")));
            logger.info("### Preloading " + customerRepository.save(new Customer("Judy Monroe")));

            // Ingredients
            Ingredient espresso_shot = new Ingredient("Espresso Shot", 3f, 10);
            Ingredient milk_foam = new Ingredient("Milk Foam", 1.5f, 8);
            Ingredient black_coffee_shot = new Ingredient("Black Coffee Shot", 2.5f, 12);
            Ingredient honey = new Ingredient("Honey", 1.5f, 7);
            Ingredient cinnamon = new Ingredient("Cinnamon", 1f, 9);
            Ingredient steamed_milk = new Ingredient("Steamed Milk", 0.5f, 13);
            Ingredient milk = new Ingredient("Milk", 0.5f, 8);
            Ingredient chocolate_syrup = new Ingredient("Chocolate Syrup", 1.5f, 10);
            Ingredient sugar = new Ingredient("Sugar", 0.5f, 20);
            Ingredient whipped_cream = new Ingredient("Whipped Cream", 1.0f, 6);

            logger.info("*** Inserting some ingredients ***");
            logger.info("### Preloading " + ingredientRepository.save(espresso_shot));
            logger.info("### Preloading " + ingredientRepository.save(milk_foam));
            logger.info("### Preloading " + ingredientRepository.save(black_coffee_shot));
            logger.info("### Preloading " + ingredientRepository.save(honey));
            logger.info("### Preloading " + ingredientRepository.save(cinnamon));
            logger.info("### Preloading " + ingredientRepository.save(steamed_milk));
            logger.info("### Preloading " + ingredientRepository.save(milk));
            logger.info("### Preloading " + ingredientRepository.save(chocolate_syrup));
            logger.info("### Preloading " + ingredientRepository.save(sugar));
            logger.info("### Preloading " + ingredientRepository.save(whipped_cream));

            // Premade Recipies
            Recipe espressoR = new Recipe(1, new HashMap<>() {{ put(espresso_shot.getName(), 2); }} );
            Recipe macchiatoR = new Recipe(2, new HashMap<>() {{ put(espresso_shot.getName(), 2); put(milk.getName(), 1); }});
            Recipe coffee_mielR = new Recipe(3, new HashMap<>() {{ put(black_coffee_shot.getName(), 2); put(honey.getName(), 1);
                                                                        put(cinnamon.getName(), 1); put(steamed_milk.getName(), 1); }} );
            Recipe coffee_latteR = new Recipe(4, new HashMap<>() {{ put(espresso_shot.getName(), 1); put(steamed_milk.getName(), 2); put(milk_foam.getName(), 1); }} );
            Recipe cappuccinoR = new Recipe(5, new HashMap<>() {{ put(espresso_shot.getName(), 1); put(steamed_milk.getName(), 1); put(milk_foam.getName(), 2); }} );

            logger.info("*** Inserting some recipes ***");
            logger.info("### Preloading " + recipeRepository.save(espressoR));
            logger.info("### Preloading " + recipeRepository.save(macchiatoR));
            logger.info("### Preloading " + recipeRepository.save(coffee_mielR));
            logger.info("### Preloading " + recipeRepository.save(coffee_latteR));
            logger.info("### Preloading " + recipeRepository.save(cappuccinoR));

            Beverage espresso = new Beverage(espressoR.getId(), "Espresso");
            Beverage macchiatto = new Beverage(macchiatoR.getId(), "Macchiatto");
            Beverage coffee_miel = new Beverage(coffee_mielR.getId(), "Coffee Miel");
            Beverage coffee_latte = new Beverage(coffee_latteR.getId(), "Coffee Latte");
            Beverage cappuccino = new Beverage(cappuccinoR.getId(), "Cappuccino");

            logger.info("*** Inserting some beverages ***");
            logger.info("### Preloading " + beverageRepository.save(espresso));
            logger.info("### Preloading " + beverageRepository.save(macchiatto));
            logger.info("### Preloading " + beverageRepository.save(coffee_miel));
            logger.info("### Preloading " + beverageRepository.save(coffee_latte));
            logger.info("### Preloading " + beverageRepository.save(cappuccino));
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
