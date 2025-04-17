package com.minarite.teril;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import net.minecraftforge.event.entity.living.LivingFallEvent;


@Mod(MainClassOfMod.MOD_ID)
public class MainClassOfMod {

    public static final String MOD_ID = "teril";

    public static boolean nightVisionActive = false;
    public static boolean flyActive = false;
    public static boolean noFallActive = false;

    public MainClassOfMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (event.getKey() == GLFW.GLFW_KEY_X && event.getAction() == GLFW.GLFW_PRESS) {
            replaceBlocksAroundPlayer();
            replaceBlocksAroundPlayerAdditional();
        }
        if (event.getKey() == GLFW.GLFW_KEY_L && event.getAction() == GLFW.GLFW_PRESS) {
            toggleNightVision();
        }
        if (event.getKey() == GLFW.GLFW_KEY_R && event.getAction() == GLFW.GLFW_PRESS) {
            toggleFly();
        }
        if (event.getKey() == GLFW.GLFW_KEY_C && event.getAction() == GLFW.GLFW_PRESS) {
            mc.player.jumpFromGround();
        }
        if (event.getKey() == GLFW.GLFW_KEY_N && event.getAction() == GLFW.GLFW_PRESS) {
            toggleNoFall();
        }
    }

    private void replaceBlocksAroundPlayer() {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        BlockPos playerPos = mc.player.blockPosition();
        int radius = 25;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    assert mc.level != null;
                    BlockState state = mc.level.getBlockState(pos);
                    //
                    //
                    // Список Обычных блоков на Стекло, то-есть на (minecraft:glass) //
                    if
                    (state.is(Blocks.STONE) ||                      // Камень
                            state.is(Blocks.DIRT) ||                        // Земля
                            state.is(Blocks.COARSE_DIRT) ||                 // Каменистая земля
                            state.is(Blocks.ROOTED_DIRT) ||                 // Корнистая земля
                            state.is(Blocks.DIRT_PATH) ||                   // Тропинка
                            state.is(Blocks.PODZOL) ||                      // Подзол
                            state.is(Blocks.MYCELIUM) ||                    // Мицелий
                            state.is(Blocks.MUSHROOM_STEM) ||               // Ножка гриба
                            state.is(Blocks.RED_MUSHROOM_BLOCK) ||          // Блок красного гриба
                            state.is(Blocks.BROWN_MUSHROOM_BLOCK) ||        // Блок коричневого гриба
                            state.is(Blocks.DRIPSTONE_BLOCK) ||             // Натёчный камень
                            state.is(Blocks.GRANITE) ||                     // Гранит
                            state.is(Blocks.DIORITE) ||                     // Диорит
                            state.is(Blocks.GRASS_BLOCK) ||                 // Дёрн
                            state.is(Blocks.ANDESITE) ||                    // Андезит
                            state.is(Blocks.DEEPSLATE) ||                   // Глубинный сланец
                            state.is(Blocks.CALCITE) ||                     // Кальцит
                            state.is(Blocks.BASALT) ||                      // Базальт
                            state.is(Blocks.SMOOTH_BASALT) ||               // Гладкий базальт
                            state.is(Blocks.RED_SAND) ||                    // Красный песок
                            state.is(Blocks.SANDSTONE) ||                   // Песчаник
                            state.is(Blocks.CHISELED_SANDSTONE) ||          // Резной песчаник
                            state.is(Blocks.CUT_SANDSTONE) ||               // Пиленый песчаник
                            state.is(Blocks.SANDSTONE_SLAB) ||              // Песчаниковая плита
                            state.is(Blocks.CUT_SANDSTONE_SLAB) ||          // Плита из пиленого песчаника
                            state.is(Blocks.TUFF) ||                        // Туф
                            state.is(Blocks.BLACKSTONE) ||                         // Чернит
                            state.is(Blocks.POLISHED_BLACKSTONE) ||                // Полированный чернит
                            state.is(Blocks.CHISELED_POLISHED_BLACKSTONE) ||       // Резной полированный чернит
                            state.is(Blocks.POLISHED_BLACKSTONE_BRICKS) ||         // Полированно-чернитные кирпичи
                            state.is(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS) || // Потрескавшиеся полированно-чернитные кирпичи
                            //
                            state.is(Blocks.GRAVEL) ||                      // Гравий
                            state.is(Blocks.SAND) ||                        // Песок
                            state.is(Blocks.COBBLESTONE) ||                 // Булыжник
                            //
                            state.is(Blocks.DEAD_TUBE_CORAL_BLOCK) ||       // Блок мёртвого трубчатого коралла
                            state.is(Blocks.DEAD_BRAIN_CORAL_BLOCK) ||      // Блок мёртвого коралла-мозговика
                            state.is(Blocks.DEAD_BUBBLE_CORAL_BLOCK) ||     // Блок мёртвого пузырчатого коралла
                            state.is(Blocks.DEAD_FIRE_CORAL_BLOCK) ||       // Блок мёртвого огненного коралла
                            state.is(Blocks.DEAD_HORN_CORAL_BLOCK) ||       // Блок мёртвого рогового коралла
                            state.is(Blocks.TUBE_CORAL_BLOCK) ||            // Блок трубчатого коралла
                            state.is(Blocks.BRAIN_CORAL_BLOCK) ||           // Блок коралла-мозговика
                            state.is(Blocks.BUBBLE_CORAL_BLOCK) ||          // Блок пузырчатого коралла
                            state.is(Blocks.FIRE_CORAL_BLOCK) ||            // Блок огненного коралла
                            state.is(Blocks.HORN_CORAL_BLOCK) ||            // Блок рогового коралла
                            //
                            state.is(Blocks.TERRACOTTA) ||                      // Терракота
                            state.is(Blocks.WHITE_TERRACOTTA) ||                // Белая керамика
                            state.is(Blocks.ORANGE_TERRACOTTA) ||               // Оранжевая керамика
                            state.is(Blocks.MAGENTA_TERRACOTTA) ||              // Сиреневая керамика
                            state.is(Blocks.LIGHT_BLUE_TERRACOTTA) ||           // Светло-синяя керамика
                            state.is(Blocks.YELLOW_TERRACOTTA) ||               // Жёлтая керамика
                            state.is(Blocks.LIME_TERRACOTTA) ||                 // Лаймовая керамика
                            state.is(Blocks.PINK_TERRACOTTA) ||                 // Розовая керамика
                            state.is(Blocks.GRAY_TERRACOTTA) ||                 // Серая керамика
                            state.is(Blocks.LIGHT_GRAY_TERRACOTTA) ||           // Светло-серая керамика
                            state.is(Blocks.CYAN_TERRACOTTA) ||                 // Бирюзовая керамика
                            state.is(Blocks.PURPLE_TERRACOTTA) ||               // Фиолетовая керамика
                            state.is(Blocks.BLUE_TERRACOTTA) ||                 // Синяя керамика
                            state.is(Blocks.BROWN_TERRACOTTA) ||                // Коричневая керамика
                            state.is(Blocks.GREEN_TERRACOTTA) ||                // Зелёная керамика
                            state.is(Blocks.RED_TERRACOTTA) ||                  // Красная керамика
                            state.is(Blocks.BLACK_TERRACOTTA) ||                // Чёрная керамика
                            //
                            state.is(Blocks.WHITE_GLAZED_TERRACOTTA) ||         // Белая глазурованная керамика
                            state.is(Blocks.ORANGE_GLAZED_TERRACOTTA) ||        // Оранжевая глазурованная керамика
                            state.is(Blocks.MAGENTA_GLAZED_TERRACOTTA) ||       // Сиреневая глазурованная керамика
                            state.is(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA) ||    // Светло-синяя глазурованная керамика
                            state.is(Blocks.YELLOW_GLAZED_TERRACOTTA) ||        // Жёлтая глазурованная керамика
                            state.is(Blocks.LIME_GLAZED_TERRACOTTA) ||          // Лаймовая глазурованная керамика
                            state.is(Blocks.PINK_GLAZED_TERRACOTTA) ||          // Розовая глазурованная керамика
                            state.is(Blocks.GRAY_GLAZED_TERRACOTTA) ||          // Серая глазурованная керамика
                            state.is(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA) ||    // Светло-серая глазурованная керамика
                            state.is(Blocks.CYAN_GLAZED_TERRACOTTA) ||          // Бирюзовая глазурованная керамика
                            state.is(Blocks.PURPLE_GLAZED_TERRACOTTA) ||        // Фиолетовая глазурованная керамика
                            state.is(Blocks.BLUE_GLAZED_TERRACOTTA) ||          // Синяя глазурованная керамика
                            state.is(Blocks.BROWN_GLAZED_TERRACOTTA) ||         // Коричневая глазурованная керамика
                            state.is(Blocks.GREEN_GLAZED_TERRACOTTA) ||         // Зелёная глазурованная керамика
                            state.is(Blocks.RED_GLAZED_TERRACOTTA) ||           // Красная глазурованная керамика
                            state.is(Blocks.BLACK_GLAZED_TERRACOTTA) ||         // Чёрная глазурованная керамика
                            //
                            state.is(Blocks.OAK_LOG) ||                         // Дуб
                            state.is(Blocks.SPRUCE_LOG) ||                      // Ель
                            state.is(Blocks.BIRCH_LOG) ||                       // Берёза
                            state.is(Blocks.JUNGLE_LOG) ||                      // Тропическое дерево
                            state.is(Blocks.ACACIA_LOG) ||                      // Акация
                            state.is(Blocks.DARK_OAK_LOG) ||                    // Тёмный дуб
                            state.is(Blocks.MANGROVE_LOG) ||                    // Мангровое дерево
                            state.is(Blocks.CRIMSON_STEM) ||                    // Багровый ствол
                            state.is(Blocks.WARPED_STEM) ||                     // Искажённый ствол
                            //
                            state.is(Blocks.STRIPPED_OAK_LOG) ||                // Оголённый дуб
                            state.is(Blocks.STRIPPED_SPRUCE_LOG) ||             // Оголённая ель
                            state.is(Blocks.STRIPPED_BIRCH_LOG) ||              // Оголённая берёза
                            state.is(Blocks.STRIPPED_JUNGLE_LOG) ||             // Оголённое тропическое дерево
                            state.is(Blocks.STRIPPED_ACACIA_LOG) ||             // Оголённая акация
                            state.is(Blocks.STRIPPED_DARK_OAK_LOG) ||           // Оголённый тёмный дуб
                            state.is(Blocks.STRIPPED_MANGROVE_LOG) ||           // Оголённое мангровое дерево
                            state.is(Blocks.STRIPPED_CRIMSON_STEM) ||           // Оголённый багровый ствол
                            state.is(Blocks.STRIPPED_WARPED_STEM) ||            // Оголённый искажённый ствол
                            //
                            state.is(Blocks.OAK_WOOD) ||                        // Дуб с корой
                            state.is(Blocks.SPRUCE_WOOD) ||                     // Ель с корой
                            state.is(Blocks.BIRCH_WOOD) ||                      // Берёза с корой
                            state.is(Blocks.JUNGLE_WOOD) ||                     // Тропическое дерево с корой
                            state.is(Blocks.ACACIA_WOOD) ||                     // Акация с корой
                            state.is(Blocks.DARK_OAK_WOOD) ||                   // Тёмный дуб с корой
                            state.is(Blocks.MANGROVE_WOOD) ||                   // Мангровое дерево с корой
                            state.is(Blocks.STRIPPED_OAK_WOOD) ||               // Оголённый дуб с корой
                            state.is(Blocks.STRIPPED_SPRUCE_WOOD) ||            // Оголённая ель с корой
                            state.is(Blocks.STRIPPED_BIRCH_WOOD) ||             // Оголённая берёза с корой
                            state.is(Blocks.STRIPPED_JUNGLE_WOOD) ||            // Оголённое тропическое дерево с корой
                            state.is(Blocks.STRIPPED_ACACIA_WOOD) ||            // Оголённая акация с корой
                            state.is(Blocks.STRIPPED_DARK_OAK_WOOD) ||          // Оголённый тёмный дуб с корой
                            state.is(Blocks.STRIPPED_MANGROVE_WOOD) ||          // Оголённое мангровое дерево с корой
                            state.is(Blocks.CRIMSON_HYPHAE) ||                  // Багровый грибной ствол с корой
                            state.is(Blocks.WARPED_HYPHAE) ||                   // Искажённый грибной ствол с корой
                            state.is(Blocks.STRIPPED_CRIMSON_HYPHAE) ||         // Оголённый багровый ствол с корой
                            state.is(Blocks.STRIPPED_WARPED_HYPHAE) ||          // Оголённый искажённый ствол с корой
                            //
                            state.is(Blocks.OAK_LEAVES) ||                      // Дубовая листва
                            state.is(Blocks.SPRUCE_LEAVES) ||                   // Еловая листва
                            state.is(Blocks.BIRCH_LEAVES) ||                    // Берёзовая листва
                            state.is(Blocks.JUNGLE_LEAVES) ||                   // Тропическая листва
                            state.is(Blocks.ACACIA_LEAVES) ||                   // Акациевая листва
                            state.is(Blocks.DARK_OAK_LEAVES) ||                 // Листва тёмного дуба
                            state.is(Blocks.MANGROVE_LEAVES) ||                 // Мангровая листва
                            //
                            state.is(Blocks.OAK_PLANKS) ||                      // Дубовые доски
                            state.is(Blocks.SPRUCE_PLANKS) ||                   // Еловые доски
                            state.is(Blocks.BIRCH_PLANKS) ||                    // Берёзовые доски
                            state.is(Blocks.JUNGLE_PLANKS) ||                   // Тропические доски
                            state.is(Blocks.ACACIA_PLANKS) ||                   // Акациевые доски
                            state.is(Blocks.DARK_OAK_PLANKS) ||                 // Тёмно-дубовые доски
                            state.is(Blocks.MANGROVE_PLANKS) ||                 // Мангровые доски
                            state.is(Blocks.CRIMSON_PLANKS) ||                  // Багровые доски
                            state.is(Blocks.WARPED_PLANKS) ||                   // Искажённые доски
                            //
                            state.is(Blocks.STONE_SLAB) ||                      // Каменная плита
                            state.is(Blocks.SANDSTONE_SLAB) ||                  // Плита из песчаника
                            state.is(Blocks.BRICK_SLAB) ||                      // Кирпичная плита
                            state.is(Blocks.NETHER_BRICK_SLAB) ||               // Плита из адского кирпича
                            state.is(Blocks.QUARTZ_SLAB) ||                     // Кварцевая плита
                            state.is(Blocks.OAK_SLAB) ||                        // Дубовая плита
                            state.is(Blocks.SPRUCE_SLAB) ||                     // Еловая плита
                            state.is(Blocks.BIRCH_SLAB) ||                      // Берёзовая плита
                            state.is(Blocks.JUNGLE_SLAB) ||                     // Тропическая плита
                            state.is(Blocks.ACACIA_SLAB) ||                     // Акацевая плита
                            state.is(Blocks.DARK_OAK_SLAB) ||                   // Плита из тёмного дуба
                            state.is(Blocks.MANGROVE_SLAB) ||                   // Мангровая плита
                            state.is(Blocks.COBBLESTONE_SLAB) ||                // Плита из булыжника
                            state.is(Blocks.STONE_BRICK_SLAB) ||                // Плита из каменных кирпичей
                            state.is(Blocks.POLISHED_ANDESITE_SLAB) ||          // Плита из отполированного андезита
                            state.is(Blocks.POLISHED_DIORITE_SLAB) ||           // Плита из отполированного диорита
                            state.is(Blocks.POLISHED_GRANITE_SLAB) ||           // Плита из отполированного гранита
                            state.is(Blocks.SMOOTH_QUARTZ_SLAB) ||              // Гладкая кварцевая плита
                            state.is(Blocks.RED_SANDSTONE_SLAB) ||              // Плита из красного песчаника
                            state.is(Blocks.PURPUR_SLAB) ||                     // Плита из пурпура
                            state.is(Blocks.PRISMARINE_SLAB) ||                 // Плита из призмарина
                            state.is(Blocks.PRISMARINE_BRICK_SLAB) ||           // Плита из кирпичей призмарина
                            state.is(Blocks.DARK_PRISMARINE_SLAB) ||            // Плита из тёмного призмарина
                            state.is(Blocks.SMOOTH_STONE_SLAB) ||               // Гладкая каменная плита
                            state.is(Blocks.ATTACHED_MELON_STEM) ||             // Прикреплённая плита из арбуза
                            state.is(Blocks.ATTACHED_PUMPKIN_STEM) ||           // Прикреплённая плита из тыквы
                            //
                            //state.is(Blocks.OAK_FENCE) ||                        // Дубовый забор
                            state.is(Blocks.SPRUCE_FENCE) ||                     // Еловый забор
                            state.is(Blocks.BIRCH_FENCE) ||                      // Берёзовый забор
                            state.is(Blocks.JUNGLE_FENCE) ||                     // Тропический забор
                            state.is(Blocks.ACACIA_FENCE) ||                     // Акациевый забор
                            state.is(Blocks.DARK_OAK_FENCE) ||                   // Тёмный дубовый забор
                            state.is(Blocks.MANGROVE_FENCE) ||                   // Мангровый забор
                            state.is(Blocks.NETHER_BRICK_FENCE) ||               // Забор из адского кирпича
                            state.is(Blocks.WARPED_FENCE) ||                     // Искажённый забор
                            state.is(Blocks.CRIMSON_FENCE) ||                    // Багровый забор
                            //
                            //
                            state.is(Blocks.STONE_STAIRS) ||                     // Каменные ступеньки
                            state.is(Blocks.SANDSTONE_STAIRS) ||                 // Ступеньки из песчаника
                            state.is(Blocks.BRICK_STAIRS) ||                     // Кирпичные ступеньки
                            state.is(Blocks.NETHER_BRICK_STAIRS) ||              // Ступеньки из адского кирпича
                            state.is(Blocks.QUARTZ_STAIRS) ||                    // Кварцевые ступеньки
                            state.is(Blocks.OAK_STAIRS) ||                       // Дубовые ступеньки
                            state.is(Blocks.SPRUCE_STAIRS) ||                    // Еловые ступеньки
                            state.is(Blocks.BIRCH_STAIRS) ||                     // Берёзовые ступеньки
                            state.is(Blocks.JUNGLE_STAIRS) ||                    // Тропические ступеньки
                            state.is(Blocks.ACACIA_STAIRS) ||                    // Акациевые ступеньки
                            state.is(Blocks.DARK_OAK_STAIRS) ||                  // Ступеньки из тёмного дуба
                            state.is(Blocks.MANGROVE_STAIRS) ||                  // Мангровые ступеньки
                            state.is(Blocks.COBBLESTONE_STAIRS) ||               // Ступеньки из булыжника
                            state.is(Blocks.STONE_BRICK_STAIRS) ||               // Ступеньки из каменных кирпичей
                            state.is(Blocks.POLISHED_ANDESITE_STAIRS) ||         // Ступеньки из отполированного андезита
                            state.is(Blocks.POLISHED_DIORITE_STAIRS) ||          // Ступеньки из отполированного диорита
                            state.is(Blocks.POLISHED_GRANITE_STAIRS) ||          // Ступеньки из отполированного гранита
                            state.is(Blocks.SMOOTH_QUARTZ_STAIRS) ||             // Гладкие кварцевые ступеньки
                            state.is(Blocks.RED_SANDSTONE_STAIRS) ||             // Ступеньки из красного песчаника
                            state.is(Blocks.PURPUR_STAIRS) ||                    // Ступеньки из пурпура
                            state.is(Blocks.PRISMARINE_STAIRS) ||                // Ступеньки из призмарина
                            state.is(Blocks.PRISMARINE_BRICK_STAIRS) ||          // Ступеньки из кирпичей призмарина
                            state.is(Blocks.DARK_PRISMARINE_STAIRS) ||           // Ступеньки из тёмного призмарина
                            //
                            state.is(Blocks.PRISMARINE) ||                        // Призмарин
                            state.is(Blocks.PRISMARINE_WALL) ||                   // Стена из призмарина
                            //
                            state.is(Blocks.STONE_BRICKS) ||                      // Каменные кирпичи
                            //state.is(Blocks.MOSSY_STONE_BRICKS) ||                // Моховые каменные кирпичи
                            state.is(Blocks.CRACKED_STONE_BRICKS) ||              // Треснутые каменные кирпичи
                            state.is(Blocks.CHISELED_STONE_BRICKS) ||             // Гравированные каменные кирпичи
                            //
                            state.is(Blocks.NETHER_BRICKS) ||                     // Незерские кирпичи
                            state.is(Blocks.CRACKED_NETHER_BRICKS) ||             // Треснувшие незерские кирпичи
                            state.is(Blocks.CHISELED_NETHER_BRICKS) ||            // Гравированные незерские кирпичи
                            state.is(Blocks.NETHER_BRICK_SLAB) ||                 // Плита из незерских кирпичей
                            state.is(Blocks.NETHER_BRICK_STAIRS) ||               // Ступеньки из незерских кирпичей
                            state.is(Blocks.NETHER_BRICK_WALL) ||                 // Стена из незерских кирпичей
                            //
                            state.is(Blocks.POLISHED_DEEPSLATE) ||                // Полированный глубинный сланец
                            state.is(Blocks.DEEPSLATE_BRICKS) ||                  // Глубинносланцевые кирпичи
                            state.is(Blocks.DEEPSLATE_BRICK_SLAB) ||              // Плита из глубиносланцевых кирпичей
                            state.is(Blocks.DEEPSLATE_BRICK_STAIRS) ||            // Ступеньки из глубиносланцевых кирпичей
                            state.is(Blocks.DEEPSLATE_BRICK_WALL) ||              // Стена из глубиносланцевых кирпичей
                            state.is(Blocks.DEEPSLATE_TILES) ||                   // Глубинносланцевый плитняк
                            state.is(Blocks.CHISELED_DEEPSLATE) ||                // Резной глубинный сланец
                            state.is(Blocks.REINFORCED_DEEPSLATE) ||              // Укреплённый глубинный сланец
                            //
                            state.is(Blocks.POLISHED_DEEPSLATE_STAIRS) ||         // Ступеньки из отполированного глубинного сланца
                            state.is(Blocks.DEEPSLATE_BRICK_STAIRS) ||            // Ступеньки из глубиносланцевых кирпичей
                            //
                            state.is(Blocks.NETHERRACK) ||                  // Незерак

                            //
                            state.is(Blocks.SCULK))                         // Скалк
                    {
                        mc.level.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private void replaceBlocksAroundPlayerAdditional() {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        BlockPos playerPos = mc.player.blockPosition();
        int radius = 25;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = playerPos.offset(x, y, z);
                    assert mc.level != null;
                    BlockState state = mc.level.getBlockState(pos);
                    //
                    // Список Выбранных блоков на Белое стекло, то-есть на (minecraft:white_stained_glass) //
                    if
                            (state.is(Blocks.COBBLED_DEEPSLATE) ||          // Колотый глубинный сланец
                            //
                            state.is(Blocks.WHITE_WOOL) ||                      // Белая шерсть
                            state.is(Blocks.ORANGE_WOOL) ||                     // Оранжевая шерсть
                            state.is(Blocks.MAGENTA_WOOL) ||                    // Сиреневая шерсть
                            state.is(Blocks.LIGHT_BLUE_WOOL) ||                 // Светло-синяя шерсть
                            state.is(Blocks.YELLOW_WOOL) ||                     // Жёлтая шерсть
                            state.is(Blocks.LIME_WOOL) ||                       // Лаймовая шерсть
                            state.is(Blocks.PINK_WOOL) ||                       // Розовая шерсть
                            state.is(Blocks.GRAY_WOOL) ||                       // Серая шерсть
                            state.is(Blocks.LIGHT_GRAY_WOOL) ||                 // Светло-серая шерсть
                            state.is(Blocks.CYAN_WOOL) ||                       // Бирюзовая шерсть
                            state.is(Blocks.PURPLE_WOOL) ||                     // Фиолетовая шерсть
                            state.is(Blocks.BLUE_WOOL) ||                       // Синяя шерсть
                            state.is(Blocks.BROWN_WOOL) ||                      // Коричневая шерсть
                            state.is(Blocks.GREEN_WOOL) ||                      // Зелёная шерсть
                            state.is(Blocks.RED_WOOL) ||                        // Красная шерсть
                            state.is(Blocks.BLACK_WOOL) ||                      // Чёрная шерсть
                            //
                            state.is(Blocks.WHITE_CONCRETE) ||                  // Белый бетон
                            state.is(Blocks.ORANGE_CONCRETE) ||                 // Оранжевый бетон
                            state.is(Blocks.MAGENTA_CONCRETE) ||                // Сиреневый бетон
                            state.is(Blocks.LIGHT_BLUE_CONCRETE) ||             // Светло-синий бетон
                            state.is(Blocks.YELLOW_CONCRETE) ||                 // Жёлтый бетон
                            state.is(Blocks.LIME_CONCRETE) ||                   // Лаймовый бетон
                            state.is(Blocks.PINK_CONCRETE) ||                   // Розовый бетон
                            state.is(Blocks.GRAY_CONCRETE) ||                   // Серый бетон
                            state.is(Blocks.LIGHT_GRAY_CONCRETE) ||             // Светло-серый бетон
                            state.is(Blocks.CYAN_CONCRETE) ||                   // Бирюзовый бетон
                            state.is(Blocks.PURPLE_CONCRETE) ||                 // Фиолетовый бетон
                            state.is(Blocks.BLUE_CONCRETE) ||                   // Синий бетон
                            state.is(Blocks.BROWN_CONCRETE) ||                  // Коричневый бетон
                            state.is(Blocks.GREEN_CONCRETE) ||                  // Зелёный бетон
                            state.is(Blocks.RED_CONCRETE) ||                    // Красный бетон
                            state.is(Blocks.BLACK_CONCRETE) ||                  // Чёрный бетон
                            //
                            state.is(Blocks.WHITE_CONCRETE_POWDER) ||           // Белый сухой бетон
                            state.is(Blocks.ORANGE_CONCRETE_POWDER) ||          // Оранжевый сухой бетон
                            state.is(Blocks.MAGENTA_CONCRETE_POWDER) ||         // Сиреневый сухой бетон
                            state.is(Blocks.LIGHT_BLUE_CONCRETE_POWDER) ||      // Светло-синий сухой бетон
                            state.is(Blocks.YELLOW_CONCRETE_POWDER) ||          // Жёлтый сухой бетон
                            state.is(Blocks.LIME_CONCRETE_POWDER) ||            // Лаймовый сухой бетон
                            state.is(Blocks.PINK_CONCRETE_POWDER) ||            // Розовый сухой бетон
                            state.is(Blocks.GRAY_CONCRETE_POWDER) ||            // Серый сухой бетон
                            state.is(Blocks.LIGHT_GRAY_CONCRETE_POWDER) ||      // Светло-серый сухой бетон
                            state.is(Blocks.CYAN_CONCRETE_POWDER) ||            // Бирюзовый сухой бетон
                            state.is(Blocks.PURPLE_CONCRETE_POWDER) ||          // Фиолетовый сухой бетон
                            state.is(Blocks.BLUE_CONCRETE_POWDER) ||            // Синий сухой бетон
                            state.is(Blocks.BROWN_CONCRETE_POWDER) ||           // Коричневый сухой бетон
                            state.is(Blocks.GREEN_CONCRETE_POWDER) ||           // Зелёный сухой бетон
                            state.is(Blocks.RED_CONCRETE_POWDER) ||             // Красный сухой бетон
                            state.is(Blocks.BLACK_CONCRETE_POWDER) ||           // Чёрный сухой бетон
                            //
                            state.is(Blocks.BONE_BLOCK))                        // Костный блок
                    {
                        mc.level.setBlock(pos, Blocks.WHITE_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    //
                    //
                    // Список Опасных блоков на Зелёное стекло, то-есть на (minecraft:green_stained_glass) //
                    if
                            (state.is(Blocks.LAVA))                     // Лава
                    {
                        mc.level.setBlock(pos, Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    //
                    //
                    // Список (Первый) Блоков на Светло-Серое стекло, то-есть на (minecraft:light_gray_stained_glass) //
                    if
                            (state.is(Blocks.OBSIDIAN))                    // Обсидиан
                    {
                        mc.level.setBlock(pos, Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState(), 3);
                    }
                    //
                    //
                    // Список (Третий) Ненужных блоков на Воздух, то-есть на (minecraft:air) //
                    if
                            (state.is(Blocks.GRASS) ||                 // Трава
                            state.is(Blocks.TALL_GRASS) ||             // Высокая трава
                            state.is(Blocks.FERN) ||                   // Папоротник
                            state.is(Blocks.LARGE_FERN) ||             // Раскидистый папоротник
                            //
                            state.is(Blocks.DANDELION) ||                   // Одуванчик
                            state.is(Blocks.POPPY) ||                   // Мак
                            state.is(Blocks.BLUE_ORCHID) ||                   // Синяя орхидея
                            state.is(Blocks.ALLIUM) ||                   // Лук-батун
                            state.is(Blocks.AZURE_BLUET) ||                   // Хаустония серая
                            state.is(Blocks.RED_TULIP) ||                   // Красный тюльпан
                            state.is(Blocks.ORANGE_TULIP) ||                   // Оранжевый тюльпан
                            state.is(Blocks.WHITE_TULIP) ||                   // Белый тюльпан
                            state.is(Blocks.PINK_TULIP) ||                   // Розовый тюльпан
                            state.is(Blocks.OXEYE_DAISY) ||                   // Ромашка
                            state.is(Blocks.CORNFLOWER) ||                   // Синий василёк
                            state.is(Blocks.LILY_OF_THE_VALLEY) ||                   // Ландыш
                            state.is(Blocks.WITHER_ROSE) ||                   // Роза визера
                            state.is(Blocks.SPORE_BLOSSOM) ||                   // Спороцвет
                            state.is(Blocks.BROWN_MUSHROOM) ||                   // Коричневый гриб
                            state.is(Blocks.RED_MUSHROOM) ||                   // Красный гриб
                            state.is(Blocks.CRIMSON_FUNGUS) ||                   // Багровый гриб
                            state.is(Blocks.WARPED_FUNGUS) ||                   // Искажённый гриб
                            //
                            state.is(Blocks.CRIMSON_ROOTS) ||                   // Багровый корни
                            state.is(Blocks.WARPED_ROOTS) ||                   // Искажённые корни
                            state.is(Blocks.NETHER_SPROUTS) ||                   // Незерские ростки
                            state.is(Blocks.WEEPING_VINES) ||                   // Плакучая лоза
                            state.is(Blocks.TWISTING_VINES) ||                   // Вьющаяся лоза
                            state.is(Blocks.HANGING_ROOTS) ||                   // Свисающие корни
                            //
                            state.is(Blocks.SUNFLOWER) ||                   // Подсолнух
                            state.is(Blocks.LILAC) ||                   // Сирень
                            state.is(Blocks.ROSE_BUSH) ||                   // Розовый куст
                            state.is(Blocks.PEONY) ||                   // Пион
                            //
                            state.is(Blocks.DEAD_BUSH))              // Мёртвый куст
                    {
                        mc.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                    //
                    //
                    // Список (Четвертый) Ненужных блоков на Воду, то-есть на (minecraft:water) //
                    if
                            (state.is(Blocks.SEAGRASS) ||               // Морская трава
                            state.is(Blocks.TALL_SEAGRASS) ||           // Высокая морская трава
                            //
                            state.is(Blocks.TUBE_CORAL) ||                    // Трубчатый коралл
                            state.is(Blocks.BRAIN_CORAL) ||                    // Коралл-мозговик
                            state.is(Blocks.BUBBLE_CORAL) ||                    // Пузырчатый коралл
                            state.is(Blocks.FIRE_CORAL) ||                    // Огненный коралл
                            state.is(Blocks.HORN_CORAL) ||                    // Роговой коралл
                            state.is(Blocks.DEAD_BRAIN_CORAL) ||                    // Мёртвый коралл-мозговик
                            state.is(Blocks.DEAD_BUBBLE_CORAL) ||                    // Мёртвый пузырчатый коралл
                            state.is(Blocks.DEAD_FIRE_CORAL) ||                    // Мёртвый огненный коралл
                            state.is(Blocks.DEAD_HORN_CORAL) ||                    // Мёртвый роговой коралл
                            state.is(Blocks.DEAD_TUBE_CORAL) ||                    // Мёртвый трубчатый коралл
                            state.is(Blocks.TUBE_CORAL_FAN) ||                    // Веерный трубчатый коралл
                            state.is(Blocks.BRAIN_CORAL_FAN) ||                    // Веерный коралл-мозговик
                            state.is(Blocks.BUBBLE_CORAL_FAN) ||                    // Веерный пузырчатый коралл
                            state.is(Blocks.FIRE_CORAL_FAN) ||                    // Веерный огненный коралл
                            state.is(Blocks.HORN_CORAL_FAN) ||                    // Веерный роговой коралл
                            state.is(Blocks.DEAD_TUBE_CORAL_FAN) ||                    // Мёртвый веерный трубчатый коралл
                            state.is(Blocks.DEAD_BRAIN_CORAL_FAN) ||                    // Мёртвый веерный коралл-мозговик
                            state.is(Blocks.DEAD_BUBBLE_CORAL_FAN) ||                    // Мёртвый веерный пузырчатый коралл
                            state.is(Blocks.DEAD_FIRE_CORAL_FAN) ||                    // Мёртвый веерный огненный коралл
                            state.is(Blocks.DEAD_HORN_CORAL_FAN) ||                    // Мёртвый веерный роговой коралл
                            //
                            state.is(Blocks.KELP) ||                    // Ламинария
                            state.is(Blocks.KELP_PLANT))                // Стебель ламинарии
                    {
                        mc.level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private void toggleNightVision() {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        if (nightVisionActive) {
            mc.player.removeEffect(MobEffects.NIGHT_VISION);
        } else {
            mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 72000, 0, false, false));
        }
        nightVisionActive = !nightVisionActive;
    }

    private void toggleFly() {
        Minecraft mc = Minecraft.getInstance();
        flyActive = !flyActive;

        if (mc.player != null) {
            mc.player.getAbilities().mayfly = flyActive;
            mc.player.getAbilities().flying = flyActive;
            mc.player.fallDistance = 0;
            syncPlayerPosition(mc.player);
            mc.player.onUpdateAbilities();
        }
    }

    private void toggleNoFall() {
        noFallActive = !noFallActive;
        System.out.println("NoFall " + (noFallActive ? "Enabled" : "Disabled"));
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof LocalPlayer player) {
            if (noFallActive || (flyActive && player.getAbilities().flying)) {
                event.setCanceled(true);
                event.setDamageMultiplier(0.0F);
                player.fallDistance = 0;

                // Принудительная синхронизация
                syncPlayerPosition(player);
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        // Постоянная защита в режиме полета
        if (flyActive) {
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
            player.fallDistance = 0;

            // Каждые 4 тиков отправляем пакет
            if (player.tickCount % 4 == 0) {
                syncPlayerPosition(player);
            }

        }

        // Защита для NoFall
        if (noFallActive && !flyActive && player.fallDistance > 1.5F) {
            syncPlayerPosition(player);
            player.fallDistance = 0;
        }
    }

    private void syncPlayerPosition(LocalPlayer player) {
        player.connection.send(new ServerboundMovePlayerPacket.PosRot(
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getYRot(),
                player.getXRot(),
                true
        ));

        // Дополнительный пакет для надежности
        player.connection.send(new ServerboundMovePlayerPacket.StatusOnly(true));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        PoseStack poseStack = event.getPoseStack();
        int x = 1, y = 1;
        long time = System.currentTimeMillis();

        drawGradientText(poseStack, font, "TeRiL v.1.0", x, y, time);
        drawGradientText(poseStack, font, "FullBright: " + (nightVisionActive ? "ON" : "OFF"), x, y + 10, time);
        drawGradientText(poseStack, font, "Fly: " + (flyActive ? "ON" : "OFF"), x, y + 20, time);
        drawGradientText(poseStack, font, "NoFall: " + (noFallActive ? "ON" : "OFF"), x, y + 30, time);
    }

    private void drawGradientText(PoseStack poseStack, Font font, String text, int x, int y, long time) {
        int currentX = x;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float hue = (time % 5000) / 5000.0f + (i * 0.05f);
            int color = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
            font.draw(poseStack, String.valueOf(c), currentX + 1, y + 1, 0x000000);
            font.draw(poseStack, String.valueOf(c), currentX, y, color);
            currentX += font.width(String.valueOf(c));
        }
    }
}