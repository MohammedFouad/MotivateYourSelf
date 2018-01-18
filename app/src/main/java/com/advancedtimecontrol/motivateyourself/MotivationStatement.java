package com.advancedtimecontrol.motivateyourself;



public class MotivationStatement {

    private int id;
    private String name;


    public MotivationStatement() {
        super();
    }

    public MotivationStatement(int id, String name) {
        super();
        this.id = id;
        this.name = name;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MotivationStatement other = (MotivationStatement) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description="
                +  ", price=" +  "]";
    }
}

