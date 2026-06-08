package CompanyOrder;

class Item {
    private int itemNumber;
    private String title;
    private int rate;

    public Item(int itemNumber, String title, int rate) {
        this.itemNumber = itemNumber;
        this.title = title;
        this.rate = rate;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
