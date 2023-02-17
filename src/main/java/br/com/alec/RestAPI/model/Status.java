package br.com.alec.RestAPI.model;

public enum Status {
    Pending('P'), Interrupted('I'), Closed('C'), Active('A'), Inactive('N'), Deleted('X');

    private final char desc;

    Status(char desc) {
        this.desc = desc;
    }

    public char getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
