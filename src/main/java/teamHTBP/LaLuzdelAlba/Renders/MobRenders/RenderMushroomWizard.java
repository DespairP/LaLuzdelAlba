package teamHTBP.LaLuzdelAlba.Renders.MobRenders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import teamHTBP.LaLuzdelAlba.Entities.EntityMushroomWizard;
import teamHTBP.LaLuzdelAlba.Renders.MobModels.ModelMushroomWizard;

import java.util.Random;

import static teamHTBP.LaLuzdelAlba.LaLuzdelAlba.MODID;

public class RenderMushroomWizard extends MobRenderer<EntityMushroomWizard,ModelMushroomWizard> {
    private ModelMushroomWizard modelRender;

    public RenderMushroomWizard(EntityRendererManager rendererManager) {
        super(rendererManager, new ModelMushroomWizard(), 0.3F);
    }


    @Override
    public ResourceLocation getTextureLocation(EntityMushroomWizard entity) {
        return new ResourceLocation(MODID,"textures/entity/mushroom_wizard.png");
    }

    @Override
    public void render(EntityMushroomWizard entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packetLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packetLight);
    }


    @Override
    protected void setupRotations(EntityMushroomWizard entityMushroomWizard, MatrixStack matrixStack, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        super.setupRotations(entityMushroomWizard, matrixStack, p_225621_3_, p_225621_4_, p_225621_5_);
    }
}
