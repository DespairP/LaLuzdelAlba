package teamHTBP.LaLuzdelAlba.Renders.MobModels;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import teamHTBP.LaLuzdelAlba.Entities.EntityMushroomWizard;

/**由BlockBench生成的模型*/
public class ModelMushroomWizard extends EntityModel<EntityMushroomWizard> {
    private ModelRenderer leg_right;
    private ModelRenderer leg_left;
    private ModelRenderer body;
    private ModelRenderer bone;
    private ModelRenderer arm_left;
    private ModelRenderer arm_right;

    public ModelMushroomWizard(){
        leg_right = new ModelRenderer(this);
        leg_right.setTexSize(64,64);
        leg_right.setPos(-1.25F, 21.0F, 0.0F);
        leg_right.texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        leg_left = new ModelRenderer(this);
        leg_left.setTexSize(64,64);
        leg_left.setPos(1.25F, 21.0F, 0.0F);
        leg_left.texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

        body = new ModelRenderer(this);
        body.setTexSize(64,64);
        body.setPos(0.0F, 19.25F, 0.0F);
        body.texOffs(0, 9).addBox(-2.5F, -1.5F, -2.5F, 5.0F, 4.0F, 5.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setTexSize(64,64);
        bone.setPos(0.0F, -1.25F, 0.0F);
        body.addChild(bone);
        setRotationAngle(bone, 0.0F, 0.0F, 0.0873F);
        bone.texOffs(0, 18).addBox(-4.0F, -1.75F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
        bone.texOffs(0, 28).addBox(-3.0F, -2.75F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
        bone.texOffs(32, 18).addBox(-3.5F, -0.25F, -3.5F, 7.0F, 3.0F, 7.0F, 0.0F, false);

        arm_left = new ModelRenderer(this);
        arm_left.setTexSize(64,64);
        arm_left.setPos(1.0F, 19.25F, 0.0F);
        setRotationAngle(arm_left, 0.0F, 0.0F, -0.48F);
        arm_left.texOffs(0, 5).addBox(0.25F, 0.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        arm_right = new ModelRenderer(this);
        arm_right.setTexSize(64,64);
        arm_right.setPos(-1.0F, 19.25F, 0.0F);
        setRotationAngle(arm_right, 0.0F, 0.0F, 0.48F);
        arm_right.texOffs(0, 5).addBox(-1.25F, 0.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, true);

    }

    @Override
    public void prepareMobModel(EntityMushroomWizard entityMushroomWizard, float p_212843_2_, float p_212843_3_, float partialTick) {
        super.prepareMobModel(entityMushroomWizard, p_212843_2_, p_212843_3_, partialTick);
        this.body.xRot = entityMushroomWizard.getViewXRot(partialTick) * 1.2F;
        this.body.yRot = 6.0F + entityMushroomWizard.getYHeadRot() * 9.0F;
        if(entityMushroomWizard.isSit()){
            this.leg_right.xRot = -1F;
            this.leg_right.yRot = 1f;
            this.leg_right.zRot = 0.3f;
            this.leg_left.xRot = -1f;
            this.leg_right.y = 21.75f;
            this.leg_left.y = 21.75f;
            this.body.y = 20;
        }else{
            this.leg_right.xRot = 0;
            this.leg_right.yRot = 0;
            this.leg_right.zRot = 0;
            this.leg_left.xRot = 0;
            this.leg_right.y = 21;
            this.leg_left.y = 21;
            this.body.y = 19.25f;
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        leg_right.render(matrixStack, buffer, packedLight, packedOverlay);
        leg_left.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        arm_left.render(matrixStack, buffer, packedLight, packedOverlay);
        arm_right.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setupAnim(EntityMushroomWizard entityMushroomWizard, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        body.xRot = headPitch * ((float)Math.PI / 180F);
        body.yRot = netHeadYaw * ((float)Math.PI / 180F);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }



}
