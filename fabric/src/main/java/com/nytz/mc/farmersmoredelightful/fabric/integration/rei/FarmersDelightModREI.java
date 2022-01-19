package com.nytz.mc.farmersmoredelightful.fabric.integration.rei;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.client.screen.CookingPotScreen;
import com.nhoryzon.mc.farmersdelight.integration.rei.cooking.CookingRecipeCategory;
import com.nhoryzon.mc.farmersdelight.integration.rei.cooking.CookingRecipeDisplay;
import com.nhoryzon.mc.farmersdelight.integration.rei.cutting.CuttingRecipeCategory;
import com.nhoryzon.mc.farmersdelight.integration.rei.cutting.CuttingRecipeDisplay;
import com.nhoryzon.mc.farmersdelight.integration.rei.decomposition.DecompositionRecipeCategory;
import com.nhoryzon.mc.farmersdelight.integration.rei.decomposition.DecompositionRecipeDisplay;
import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.nhoryzon.mc.farmersdelight.registry.RecipeTypesRegistry;
import com.nhoryzon.mc.farmersdelight.tag.Tags;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;

import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class FarmersDelightModREI implements REIClientPlugin {

    public static final CategoryIdentifier<CookingRecipeDisplay> COOKING = CategoryIdentifier.of(FarmersDelightMod.MOD_ID, "cooking");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.addWorkstations(COOKING, EntryStacks.of(BlocksRegistry.COOKING_POT.get()));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(89, 25, 24, 17), CookingPotScreen.class, COOKING);
    }

}
