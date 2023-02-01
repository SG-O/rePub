package de.sg_o.lib.rePub.opfPack;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public enum Relator {
    ABR ("abr", "Abridger"),
    ACP ("acp", "Art copyist"),
    ACT ("act", "Actor"),
    ADI ("adi", "Art director"),
    ADP ("adp", "Adapter"),
    AFT ("aft", "Author of afterword, colophon, etc."),
    ANL ("anl", "Analyst"),
    ANM ("anm", "Animator"),
    ANN ("ann", "Annotator"),
    ANT ("ant", "Bibliographic antecedent"),
    APE ("ape", "Appellee"),
    APL ("apl", "Appellant"),
    APP ("app", "Applicant"),
    AQT ("aqt", "Author in quotations or text abstracts"),
    ARC ("arc", "Architect"),
    ARD ("ard", "Artistic director"),
    ARR ("arr", "Arranger"),
    ART ("art", "Artist"),
    ASG ("asg", "Assignee"),
    ASN ("asn", "Associated name"),
    ATO ("ato", "Autographer"),
    ATT ("att", "Attributed name"),
    AUC ("auc", "Auctioneer"),
    AUD ("aud", "Author of dialog"),
    AUI ("aui", "Author of introduction, etc."),
    AUS ("aus", "Screenwriter"),
    AUT ("aut", "Author"),
    BDD ("bdd", "Binding designer"),
    BJD ("bjd", "Bookjacket designer"),
    BKD ("bkd", "Book designer"),
    BKP ("bkp", "Book producer"),
    BLW ("blw", "Blurb writer"),
    BND ("bnd", "Binder"),
    BPD ("bpd", "Bookplate designer"),
    BRD ("brd", "Broadcaster"),
    BRL ("brl", "Braille embosser"),
    BSL ("bsl", "Bookseller"),
    CAS ("cas", "Caster"),
    CCP ("ccp", "Conceptor"),
    CHR ("chr", "Choreographer"),
    CLB ("clb", "Collaborator"),
    CLI ("cli", "Client"),
    CLL ("cll", "Calligrapher"),
    CLR ("clr", "Colorist"),
    CLT ("clt", "Collotyper"),
    CMM ("cmm", "Commentator"),
    CMP ("cmp", "Composer"),
    CMT ("cmt", "Compositor"),
    CND ("cnd", "Conductor"),
    CNG ("cng", "Cinematographer"),
    CNS ("cns", "Censor"),
    COE ("coe", "Contestant-appellee"),
    COL ("col", "Collector"),
    COM ("com", "Compiler"),
    CON ("con", "Conservator"),
    COR ("cor", "Collection registrar"),
    COS ("cos", "Contestant"),
    COT ("cot", "Contestant-appellant"),
    COU ("cou", "Court governed"),
    COV ("cov", "Cover designer"),
    CPC ("cpc", "Copyright claimant"),
    CPE ("cpe", "Complainant-appellee"),
    CPH ("cph", "Copyright holder"),
    CPL ("cpl", "Complainant"),
    CPT ("cpt", "Complainant-appellant"),
    CRE ("cre", "Creator"),
    CRP ("crp", "Correspondent"),
    CRR ("crr", "Corrector"),
    CRT ("crt", "Court reporter"),
    CSL ("csl", "Consultant"),
    CSP ("csp", "Consultant to a project"),
    CST ("cst", "Costume designer"),
    CTB ("ctb", "Contributor"),
    CTE ("cte", "Contestee-appellee"),
    CTG ("ctg", "Cartographer"),
    CTR ("ctr", "Contractor"),
    CTS ("cts", "Contestee"),
    CTT ("ctt", "Contestee-appellant"),
    CUR ("cur", "Curator"),
    CWT ("cwt", "Commentator for written text"),
    DBP ("dbp", "Distribution place"),
    DFD ("dfd", "Defendant"),
    DFE ("dfe", "Defendant-appellee"),
    DFT ("dft", "Defendant-appellant"),
    DGC ("dgc", "Degree committee member"),
    DGG ("dgg", "Degree granting institution"),
    DGS ("dgs", "Degree supervisor"),
    DIS ("dis", "Dissertant"),
    DLN ("dln", "Delineator"),
    DNC ("dnc", "Dancer"),
    DNR ("dnr", "Donor"),
    DPC ("dpc", "Depicted"),
    DPT ("dpt", "Depositor"),
    DRM ("drm", "Draftsman"),
    DRT ("drt", "Director"),
    DSR ("dsr", "Designer"),
    DST ("dst", "Distributor"),
    DTC ("dtc", "Data contributor"),
    DTE ("dte", "Dedicatee"),
    DTM ("dtm", "Data manager"),
    DTO ("dto", "Dedicator"),
    DUB ("dub", "Dubious author"),
    EDC ("edc", "Editor of compilation"),
    EDM ("edm", "Editor of moving image work"),
    EDT ("edt", "Editor"),
    EGR ("egr", "Engraver"),
    ELG ("elg", "Electrician"),
    ELT ("elt", "Electrotyper"),
    ENG ("eng", "Engineer"),
    ENJ ("enj", "Enacting jurisdiction"),
    ETR ("etr", "Etcher"),
    EVP ("evp", "Event place"),
    EXP ("exp", "Expert"),
    FAC ("fac", "Facsimilist"),
    FDS ("fds", "Film distributor"),
    FLD ("fld", "Field director"),
    FLM ("flm", "Film editor"),
    FMD ("fmd", "Film director"),
    FMK ("fmk", "Filmmaker"),
    FMO ("fmo", "Former owner"),
    FMP ("fmp", "Film producer"),
    FND ("fnd", "Funder"),
    FPY ("fpy", "First party"),
    FRG ("frg", "Forger"),
    GIS ("gis", "Geographic information specialist"),
    GRT ("grt", "Graphic technician"),
    HIS ("his", "Host institution"),
    HNR ("hnr", "Honoree"),
    HST ("hst", "Host"),
    ILL ("ill", "Illustrator"),
    ILU ("ilu", "Illuminator"),
    INS ("ins", "Inscriber"),
    INV ("inv", "Inventor"),
    ISB ("isb", "Issuing body"),
    ITR ("itr", "Instrumentalist"),
    IVE ("ive", "Interviewee"),
    IVR ("ivr", "Interviewer"),
    JUD ("jud", "Judge"),
    JUG ("jug", "Jurisdiction governed"),
    LBR ("lbr", "Laboratory"),
    LBT ("lbt", "Librettist"),
    LDR ("ldr", "Laboratory director"),
    LED ("led", "Lead"),
    LEE ("lee", "Libelee-appellee"),
    LEL ("lel", "Libelee"),
    LEN ("len", "Lender"),
    LET ("let", "Libelee-appellant"),
    LGD ("lgd", "Lighting designer"),
    LIE ("lie", "Libelant-appellee"),
    LIL ("lil", "Libelant"),
    LIT ("lit", "Libelant-appellant"),
    LSA ("lsa", "Landscape architect"),
    LSE ("lse", "Licensee"),
    LSO ("lso", "Licensor"),
    LTG ("ltg", "Lithographer"),
    LYR ("lyr", "Lyricist"),
    MCP ("mcp", "Music copyist"),
    MDC ("mdc", "Metadata contact"),
    MED ("med", "Medium"),
    MFP ("mfp", "Manufacture place"),
    MFR ("mfr", "Manufacturer"),
    MOD ("mod", "Moderator"),
    MON ("mon", "Monitor"),
    MRB ("mrb", "Marbler"),
    MRK ("mrk", "Markup editor"),
    MSD ("msd", "Musical director"),
    MTE ("mte", "Metal-engraver"),
    MTK ("mtk", "Minute taker"),
    MUS ("mus", "Musician"),
    NRT ("nrt", "Narrator"),
    OPN ("opn", "Opponent"),
    ORG ("org", "Originator"),
    ORM ("orm", "Organizer"),
    OSP ("osp", "Onscreen presenter"),
    OTH ("oth", "Other"),
    OWN ("own", "Owner"),
    PAD ("pad", "Place of address"),
    PAN ("pan", "Panelist"),
    PAT ("pat", "Patron"),
    PBD ("pbd", "Publishing director"),
    PBL ("pbl", "Publisher"),
    PDR ("pdr", "Project director"),
    PFR ("pfr", "Proofreader"),
    PHT ("pht", "Photographer"),
    PLT ("plt", "Platemaker"),
    PMA ("pma", "Permitting agency"),
    PMN ("pmn", "Production manager"),
    POP ("pop", "Printer of plates"),
    PPM ("ppm", "Papermaker"),
    PPT ("ppt", "Puppeteer"),
    PRA ("pra", "Praeses"),
    PRC ("prc", "Process contact"),
    PRD ("prd", "Production personnel"),
    PRE ("pre", "Presenter"),
    PRF ("prf", "Performer"),
    PRG ("prg", "Programmer"),
    PRM ("prm", "Printmaker"),
    PRN ("prn", "Production company"),
    PRO ("pro", "Producer"),
    PRP ("prp", "Production place"),
    PRS ("prs", "Production designer"),
    PRT ("prt", "Printer"),
    PRV ("prv", "Provider"),
    PTA ("pta", "Patent applicant"),
    PTE ("pte", "Plaintiff-appellee"),
    PTF ("ptf", "Plaintiff"),
    PTH ("pth", "Patent holder"),
    PTT ("ptt", "Plaintiff-appellant"),
    PUP ("pup", "Publication place"),
    RBR ("rbr", "Rubricator"),
    RCD ("rcd", "Recordist"),
    RCE ("rce", "Recording engineer"),
    RCP ("rcp", "Addressee"),
    RDD ("rdd", "Radio director"),
    RED ("red", "Redaktor"),
    REN ("ren", "Renderer"),
    RES ("res", "Researcher"),
    REV ("rev", "Reviewer"),
    RPC ("rpc", "Radio producer"),
    RPS ("rps", "Repository"),
    RPT ("rpt", "Reporter"),
    RPY ("rpy", "Responsible party"),
    RSE ("rse", "Respondent-appellee"),
    RSG ("rsg", "Restager"),
    RSP ("rsp", "Respondent"),
    RSR ("rsr", "Restorationist"),
    RST ("rst", "Respondent-appellant"),
    RTH ("rth", "Research team head"),
    RTM ("rtm", "Research team member"),
    SAD ("sad", "Scientific advisor"),
    SCE ("sce", "Scenarist"),
    SCL ("scl", "Sculptor"),
    SCR ("scr", "Scribe"),
    SDS ("sds", "Sound designer"),
    SEC ("sec", "Secretary"),
    SGD ("sgd", "Stage director"),
    SGN ("sgn", "Signer"),
    SHT ("sht", "Supporting host"),
    SLL ("sll", "Seller"),
    SNG ("sng", "Singer"),
    SPK ("spk", "Speaker"),
    SPN ("spn", "Sponsor"),
    SPY ("spy", "Second party"),
    SRV ("srv", "Surveyor"),
    STD ("std", "Set designer"),
    STG ("stg", "Setting"),
    STL ("stl", "Storyteller"),
    STM ("stm", "Stage manager"),
    STN ("stn", "Standards body"),
    STR ("str", "Stereotyper"),
    TCD ("tcd", "Technical director"),
    TCH ("tch", "Teacher"),
    THS ("ths", "Thesis advisor"),
    TLD ("tld", "Television director"),
    TLP ("tlp", "Television producer"),
    TRC ("trc", "Transcriber"),
    TRL ("trl", "Translator"),
    TYD ("tyd", "Type designer"),
    TYG ("tyg", "Typographer"),
    UVP ("uvp", "University place"),
    VAC ("vac", "Voice actor"),
    VDG ("vdg", "Videographer"),
    VOC ("voc", "Vocalist"),
    WAC ("wac", "Writer of added commentary"),
    WAL ("wal", "Writer of added lyrics"),
    WAM ("wam", "Writer of accompanying material"),
    WAT ("wat", "Writer of added text"),
    WDC ("wdc", "Woodcutter"),
    WDE ("wde", "Wood engraver"),
    WIN ("win", "Writer of introduction"),
    WIT ("wit", "Witness"),
    WPR ("wpr", "Writer of preface"),
    WST ("wst", "Writer of supplementary textual content");

    private final String code;
    private final String definition;

    private static final Map<String, Relator> codes = new HashMap<>();

    static {
        for (Relator r : values()) {
            codes.put(r.code, r);
        }
    }

    Relator(String code, String definition) {
        this.code = code;
        this.definition = definition;
    }

    public String getCode() {
        return code;
    }

    public String getDefinition() {
        return definition;
    }

    public static Relator fromCode(String code) {
        if (code == null) return OTH;
        Relator ret = codes.get(code);
        if (ret == null) return OTH;
        return ret;
    }
}
