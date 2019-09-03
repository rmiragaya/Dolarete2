package first.tapdeveloper.dolarete;

import org.jsoup.nodes.Document;

class ResultadoDeJsoup {
    private org.jsoup.nodes.Document documentoBna;
    private org.jsoup.nodes.Document documentoDolarInfo;
    private org.jsoup.nodes.Document documentoDolarMerval;
    private org.jsoup.nodes.Document documentoDolarMervalArtengina;
    private org.jsoup.nodes.Document documentoBitcoin;
    private org.jsoup.nodes.Document documentoOro;
    private org.jsoup.nodes.Document documentoRiesgoPais;




    public ResultadoDeJsoup(Document documentoBna, Document documentoDolarInfo ) {
        this.documentoBna = documentoBna;
        this.documentoDolarInfo = documentoDolarInfo;
    }

    public ResultadoDeJsoup(Document documentoRiesgoPais, Document documentoDolarMerval, Document documentoDolarMervalArtengina, Document documentoOro, Document documentoBitcoin) {
        this.documentoDolarMerval = documentoDolarMerval;
        this.documentoDolarMervalArtengina = documentoDolarMervalArtengina;
        this.documentoBitcoin = documentoBitcoin;
        this.documentoOro = documentoOro;
        this.documentoRiesgoPais = documentoRiesgoPais;
    }

    public Document getDocumentoBna() {
        return documentoBna;
    }

    public Document getDocumentoDolarInfo() {
        return documentoDolarInfo;
    }

    public Document getDocumentoDolarMerval() {
        return documentoDolarMerval;
    }

    public Document getDocumentoDolarMervalArtengina() {
        return documentoDolarMervalArtengina;
    }

    public Document getDocumentoBitcoin() {
        return documentoBitcoin;
    }

    public Document getDocumentoOro() {
        return documentoOro;
    }

    public Document getDocumentoRiesgoPais() {
        return documentoRiesgoPais;
    }

    public int getDocumentSize(){

        return documentoBna.childNodeSize();
    }

}
