package ru.yandex.igor;

public enum Figure {
    WHITEFIGURE, BLACKFIGURE, WHITEKING, BLACKKING, EMPTYSPACE;

    private static final String WHITEFIGURE_UNICODE = " W ";
    private static final String BLACKFIGURE_UNICODE = " B ";
    private static final String WHITEKING_UNICODE = " K ";
    private static final String BLACKKING_UNICODE = " D ";
    private static final String EMPTYSPACE_UNICODE = "[ ]";

    public String getFigureUnicode(){
        switch (this){
            case WHITEFIGURE: return WHITEFIGURE_UNICODE;
            case BLACKFIGURE: return BLACKFIGURE_UNICODE;
            case EMPTYSPACE: return EMPTYSPACE_UNICODE;
            case WHITEKING: return WHITEKING_UNICODE;
            case BLACKKING: return BLACKKING_UNICODE;
            default: throw new UncheckedCheckersException();
        }
    }
    public String ifWhite(){
        switch(this){
            case BLACKFIGURE: return "B";
            case BLACKKING: return "B";
            case WHITEKING: return "W";
            case WHITEFIGURE: return "W";
            default: throw new UncheckedCheckersException();
        }
    }
}
