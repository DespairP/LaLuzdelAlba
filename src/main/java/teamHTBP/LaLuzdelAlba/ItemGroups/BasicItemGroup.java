package teamHTBP.LaLuzdelAlba.ItemGroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**一个基础物品创造栏，无基本的排序逻辑*/
public class BasicItemGroup extends ItemGroup {

    private IIconMaker maker;

    /**
     * 基本的构造方法，需传入创造栏名和一个接口的实现，
     * 接口需要实现 BasicItemGroup#IIconMaker 用于返回创造栏的图标[itemStack]
     * @param label 创造栏名称
     * @param maker 实现 返回创造栏图标 的接口，可以使用lambda来进行实现
     * */
    public BasicItemGroup(String label,IIconMaker maker) {
        this(label);
        this.maker = maker;
    }

    /**
     * 基本的构造方法
     * @deprecated 该构造方法并不能创建创造栏图标
     * */
    @Deprecated
    public BasicItemGroup(String label){
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        if(maker != null) return maker.getItemGroupIcon();
        return null;
    }

    public interface IIconMaker{
        /**返回该创造栏的图标，需要返回itemstack*/
        public ItemStack getItemGroupIcon();
    }


}
