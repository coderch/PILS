package datenmodell;

/**
 * Created by ajanzen on 13.02.2017.
 */
public enum DienstgradSort {

    S(0),
    G(1),
    OG(3),
    HG(4),
    SG(5),
    OSG(6),
    U(7),
    MT(8),
    FJ(9),
    SK(10),
    SU(11),
    OMT(12),
    F(13),
    B(14),
    FR(15),
    FRZS(16),
    OF(17),
    OB(18),
    HF(19),
    HB(20),
    OFR(21),
    OFRZS(22),
    SF(23),
    SB(24),
    OSF(25),
    OSB(26),
    L(27),
    LZS(28),
    OL(29),
    OLZS(30),
    H(31),
    KL(32),
    SH(33),
    SKL(34),
    M(35),
    KK(36),
    OTL(37),
    FK(38),
    O(39),
    KZS(40),
    BG(41),
    FADM(42),
    GM(43),
    KADM(44),
    GL(45),
    VADM(46),
    GEN(47),
    ADM(48);

    private int wert;

    DienstgradSort(int ordungswert) {
        wert = ordungswert;

    }
}
