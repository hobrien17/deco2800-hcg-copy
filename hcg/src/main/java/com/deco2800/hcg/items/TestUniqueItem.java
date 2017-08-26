package com.deco2800.hcg.items;

public class TestUniqueItem extends BasicNonstackableItem {

    private String uniqueData;
    
    public TestUniqueItem() {
        super("Unique item", 10);
    }
    
    @Override
    public String getName() {
        return String.format("%s (%s)", super.getName(), this.uniqueData);
    }
    
    public void setUniqueData(String data) {
        this.uniqueData = data;
    }
    
    @Override
    public boolean sameItem(Item item) {
        if(item instanceof TestUniqueItem) {
            TestUniqueItem castedItem = (TestUniqueItem) item;
            return castedItem.uniqueData.equals(this.uniqueData);
        }
        
        return false;
    }
}
