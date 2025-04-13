package com.minarite.teril;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "MOD_ID", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChestEsp {

    // Клавиша для включения/выключения ChestESP
    private static final KeyMapping toggleKey = new KeyMapping("key.chestesp.toggle", GLFW.GLFW_KEY_H, "key.categories.gameplay");

    // Множество для хранения позиций сундуков
    private static final Set<BlockPos> CHEST_POSITIONS = new HashSet<>();
    private static boolean chestEspEnabled = false; // Флаг для включения/выключения

    // Статическая инициализация: находим все сундуки
    static {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null) {
            BlockPos base = player.blockPosition();
            for (int x = -5; x <= 5; x++) {
                for (int y = -5; y <= 5; y++) {
                    for (int z = -5; z <= 5; z++) {
                        BlockPos pos = base.offset(x, y, z);
                        assert mc.level != null;
                        Block block = mc.level.getBlockState(pos).getBlock();
                        if (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST || block == Blocks.ENDER_CHEST) {
                            CHEST_POSITIONS.add(pos);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKeyPress(net.minecraftforge.event.TickEvent.ClientTickEvent event) {
        if (toggleKey.isDown()) {  // Проверка, нажата ли клавиша
            chestEspEnabled = !chestEspEnabled; // Переключение состояния
        }
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS || !chestEspEnabled) return;

        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        PoseStack poseStack = event.getPoseStack();
        Matrix4f matrix = poseStack.last().pose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        // Рендерим контуры сундуков
        for (BlockPos pos : CHEST_POSITIONS) {
            double x = pos.getX() - camPos.x;
            double y = pos.getY() - camPos.y;
            double z = pos.getZ() - camPos.z;

            // Контур сундука
            drawBox(buffer, matrix, x, y, z, new Color(0, 255, 0, 255));

            // Оверлей (полупрозрачный, чтобы подсветить сундук)
            drawFilledBox(buffer, matrix, x, y, z, new Color(0, 0, 255, 100));

            // Трассировка (луч к сундуку)
            drawLine(buffer, matrix, camPos, new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), new Color(255, 0, 0, 255));
        }

        tesselator.end();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private static void drawBox(BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, Color color) {
        buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
        AABB aabb = new AABB(x, y, z, x + 1, y + 1, z + 1);
        drawAABB(buffer, matrix, aabb, color);
    }

    private static void drawFilledBox(BufferBuilder buffer, Matrix4f matrix, double x, double y, double z, Color color) {
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        AABB aabb = new AABB(x, y, z, x + 1, y + 1, z + 1);
        drawFilledAABB(buffer, matrix, aabb, color);
    }

    private static void drawLine(BufferBuilder buffer, Matrix4f matrix, Vec3 from, Vec3 to, Color color) {
        buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(matrix, (float)(0.0), (float)(0.0), (float)(0.0))
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.vertex(matrix, (float)(to.x - from.x), (float)(to.y - from.y), (float)(to.z - from.z))
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
    }

    private static void drawAABB(BufferBuilder buffer, Matrix4f matrix, AABB box, Color color) {
        float r = color.getRed(), g = color.getGreen(), b = color.getBlue(), a = color.getAlpha();
        double x1 = box.minX, y1 = box.minY, z1 = box.minZ;
        double x2 = box.maxX, y2 = box.maxY, z2 = box.maxZ;

        // 12 ребер куба (контур)
        buffer.vertex(matrix, (float) x1, (float) y1, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y1, (float) z1).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x2, (float) y1, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y1, (float) z2).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x2, (float) y1, (float) z2).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x1, (float) y1, (float) z2).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x1, (float) y1, (float) z2).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x1, (float) y1, (float) z1).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x1, (float) y2, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y2, (float) z1).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x2, (float) y2, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y2, (float) z2).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x2, (float) y2, (float) z2).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x1, (float) y2, (float) z2).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x1, (float) y2, (float) z2).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x1, (float) y2, (float) z1).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x1, (float) y1, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x1, (float) y2, (float) z1).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x2, (float) y1, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y2, (float) z1).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x2, (float) y1, (float) z2).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y2, (float) z2).color(r, g, b, a).endVertex();

        buffer.vertex(matrix, (float) x1, (float) y1, (float) z2).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x1, (float) y2, (float) z2).color(r, g, b, a).endVertex();
    }

    private static void drawFilledAABB(BufferBuilder buffer, Matrix4f matrix, AABB box, Color color) {
        float r = color.getRed(), g = color.getGreen(), b = color.getBlue(), a = color.getAlpha();
        double x1 = box.minX, z1 = box.minZ;
        double x2 = box.maxX, y2 = box.maxY, z2 = box.maxZ;

        // Верхняя грань
        buffer.vertex(matrix, (float) x1, (float) y2, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y2, (float) z1).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x2, (float) y2, (float) z2).color(r, g, b, a).endVertex();
        buffer.vertex(matrix, (float) x1, (float) y2, (float) z2).color(r, g, b, a).endVertex();
    }
}
