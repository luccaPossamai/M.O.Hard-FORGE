package net.lucca.mohard.block.essenceExchanger;

import net.lucca.mohard.gui.EssenceSlot;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.init.ModContainers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EssenceExchangerMenu extends AbstractContainerMenu {

    private final Container container;
    private final ContainerData containerData;

    public EssenceExchangerMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(1), new SimpleContainerData(3));


    }

    public EssenceExchangerMenu(int p_38852_, Inventory inventory, Container container, ContainerData data) {
        super(ModContainers.ESSENCE_EXCHANGER.get(), p_38852_);
        this.container = container;
        this.containerData = data;




        this.addSlot(new EssenceSlot(container, 0, 44, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));

        }
        this.addDataSlots(data);

    }

    @Override
    public boolean stillValid(@NotNull Player p_38874_) {
        return this.container.stillValid(p_38874_);
    }

    @Nullable
    public EssenceItem getEssenceItem(){
        return container.getItem(0).getItem() instanceof EssenceItem ?
                (EssenceItem) container.getItem(0).getItem() :
                null;
    }


    public int getTime(){
        return this.containerData.get(0);
    }

    public int getBinds(){
        return this.containerData.get(1);
    }
    public boolean isActive(){
        return this.containerData.get(2) == 1;
    }


    @Override
    public ItemStack quickMoveStack(Player p_39100_, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (EssenceSlot.mayPlaceItem(itemstack)) {
                if (slotIndex != 0) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(itemstack1, 9, 44, false)) {
                        return ItemStack.EMPTY;
                    }

                }
            }


            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_39100_, itemstack1);
        }


        return itemstack;
    }
}
