package org.busko.routemanager.model.transit.gtfs;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findStopsByRoute" })
public class Stop implements GtfsFormatted, Displayable {

    @NotNull
    @Size(max = 20)
    private String stopId;

    @NotNull
    @Size(max = 100)
    private String stopName;

    @Size(max = 256)
    private String stopDesc;

    @NotNull
    @Size(max = 20)
    private String stopLat;

    @NotNull
    @Size(max = 20)
    private String stopLon;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stop")
    private Set<StopTime> stopTimes = new HashSet<StopTime>();

    @ManyToOne
    private Agency agency;

    @ManyToOne
    private Route route;

    @NotNull
    private Boolean explicitStopId;

    public Stop() {
        this.explicitStopId = false;
    }

    public Stop(String stopId, String stopName, String stopDesc, String stopLat, String stopLon) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.stopDesc = stopDesc;
        this.stopLat = stopLat;
        this.stopLon = stopLon;
        this.explicitStopId = false;
    }

    public void addStopTime(StopTime stopTime) {
        stopTime.setStop(this);
        stopTimes.add(stopTime);
    }

    public String getFullStopId() {
        if (explicitStopId) return stopId;
        String fullStopId = stopId.length() == 1 ? ("0" + stopId) : stopId;
        if (agency != null) return new StringBuilder().append(agency.getUniqueEncoding()).append("00").append(fullStopId).toString();
        if (route != null) return new StringBuilder().append(route.getAgency().getUniqueEncoding()).append(route.getUniqueEncoding()).append(fullStopId).toString();
        return stopId;
    }

    @Override
    public String getGtfsFileName() {
        return "stops.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "stop_id,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url\n" +
                "110000,Queenstown Centre,,-45.03135000000001,168.66112999999996,,\n" +
                "110001,Frankton Hub,,-45.01686394646217,168.73201174770838,,\n" +
                "110601,Willow Place / Kawarau Falls,,-45.02938421998146,168.72676265994232,,\n" +
                "110602,Loop Road,,-45.02928,168.71654447113042,,\n" +
                "110603,Mincher Road,,-45.037697903915095,168.6986909786399,,\n" +
                "110604,Balmoral,,-45.03900204769064,168.69551533583035,,\n" +
                "110605,Bay View,,-45.04162503791908,168.69280997473493,,\n" +
                "110606,Four View / Grove Lane,,-45.04580671682984,168.6808614455192,,\n" +
                "110801,Industrial Pl,,-45.01834814777666,168.66325962998792,,\n" +
                "110802,Arthurs Pt Pub / McChesney,,-44.99258375625568,168.6724962508854,,\n" +
                "110803,Shotover Top 10 Holiday Park / Atley Rd,,-44.984675293671124,168.6769997127991,,\n" +
                "110804,Coronet Hotels,,-44.98005153491561,168.68560679474456,,\n" +
                "110901,Steamer Wharf / Crowne Plaza,,-45.033173703433356,168.65746629656746,,\n" +
                "110902,Rydges Hotel,,-45.035384534853186,168.65206639546534,,\n" +
                "110903,YHA Lakefront,,-45.03656287484934,168.64983672862797,,\n" +
                "110904,Heritage Resort,,-45.03778695565622,168.6427525201765,,\n" +
                "110999,Mercure Hotel,,-45.03827,168.63974415538792,,\n" +
                "110905,Cameron Place,,-45.037677297831294,168.63512688855997,,\n" +
                "110906,Aspen Queenstown,,-45.038988820407496,168.63307919853105,,\n" +
                "110907,Fernhill / Lorden's Place,,-45.040377299649414,168.63089628858734,,\n" +
                "110908,Sunshine Bay Reserve (Treetops) Hub,,-45.045469999999995,168.62645649192814,,\n" +
                "110909,Sunshine Bay (Fernhill Rd / Williams St) to Queenstown Hub,,-45.04275761582577,168.62505132440026,,\n" +
                "111001,Quail Rise,,-45.00244325842862,168.7547841573238,,\n" +
                "111002,Lake Hayes Estate,,-44.99767485089185,168.78234117866202,,\n" +
                "111003,Amisfield Winery,,-44.987404847012556,168.81486975244195,,\n" +
                "111004,Manse Rd,,-44.94170363723466,168.81972074011765,,\n" +
                "111006,Adamson Drive.,,-44.94458922934451,168.83494065090417,,\n" +
                "111005,Ramshaw Lane,,-44.938342521820346,168.83379889040384,,\n" +
                "111102,Copthorne Hotel / Millienium,,-45.033929999999984,168.66683999999998,,\n" +
                "111103,Kingsgate Hotel,,-45.034536728125026,168.67125416067688,,\n" +
                "111104,Hensman Rd / Oaks Shores,,-45.02901473146804,168.6865817127043,,\n" +
                "111106,Sherwood Manor,,-45.023695275135616,168.69832714108395,,\n" +
                "111107,Marina Heights,,-45.01711249542158,168.71715101283667,,\n" +
                "111109,Airport,,-45.0219809231576,168.73870884605344,,\n" +
                "111197,Douglas Street,,-45.02599740233528,168.73623760105238,,\n" +
                "111198,Robertson Street,,-45.0262010634965,168.73960571057535,,\n" +
                "111199,Remarkable Park Shopping Centre,,-45.02670948408744,168.74314689713947,,\n" +
                "111201,Events Centre,,-45.016602088711714,168.73639895303347,,\n" +
                "111202,Quail Rise,,-44.99614109175915,168.75481791525124,,\n" +
                "111203,Lake Hayes Estate / Narrin Square,,-45.00183042890945,168.7894391421828,,\n" +
                "Normanby,Normanby,,-45.843181030895536,170.54186582565308,,\n" +
                "OctagonStand1,Octagon Stand 1,,-45.87501103746416,170.50369262695312,,\n" +
                "StClair,St Clair,,-45.91151837170251,170.48890829086304,,\n" +
                "BelgraveCres,Belgrave Crescent,,-45.87096436830771,170.48243509343413,,\n" +
                "UpperHighgate,Upper Highgate,,-45.868999290226654,170.4866938783284,,\n" +
                "FreshChoiceRoslyn,Fresh Choice Roslyn,,-45.8678073867509,170.48896360516844,,\n" +
                "AmityHealthCentre,Amity Health Centre,,-45.86617227781143,170.49169446069322,,\n" +
                "ObanSt,Oban St,,-45.86430128279292,170.49365913957135,,\n" +
                "ColumbaCollege,Columba College,,-45.862795527003314,170.49496109041584,,\n" +
                "FairfaxSt,Fairfax St,,-45.861513592618344,170.49577223435935,,\n" +
                "LynnSt,Lynn St,,-45.859939748120766,170.49672336254196,,\n" +
                "MaoriHill,Maori Hill,,-45.85808568653667,170.50006455018578,,\n" +
                "GraterSt,Grater St,,-45.856748702269385,170.49901418811598,,\n" +
                "PassmoreCres,Passmore Cres,,-45.855111616912424,170.50047846927743,,\n" +
                "CairnhillSt,Cairnhill St,,-45.85352263575325,170.50127190184114,,\n" +
                "CanningtonRd,Cannington,,-45.85424147962282,170.5026680731985,,\n" +
                "BrawviewCres,Brawview Cres,,-45.85536895811531,170.50477544080528,,\n" +
                "StonelawTce,Stonelaw Tce,,-45.856363451559915,170.5067074540335,,\n" +
                "ProspectPark,Prospect Park,,-45.857069442434,170.50759335703495,,\n" +
                "BrownvilleCres,Brownville Cres,,-45.856700861271925,170.50485235813895,,\n" +
                "MaoriHillBusShelter,Maori Hill Bus Shelter,,-45.85851985809208,170.50009508575437,,\n" +
                "BaxterSt,Baxter St,,-45.859740352116944,170.50197335213636,,\n" +
                "TolcarneAve,Tolcarne Ave,,-45.86094543469579,170.50429245569595,,\n" +
                "RoyalTce,Royal Tce,,-45.86538266980296,170.50439876078215,,\n" +
                "KyberPass,Kyber Pass,,-45.86414771020732,170.50587700657712,,\n" +
                "QueenSt,Queen St,,-45.86307278426427,170.5078638435053,,\n" +
                "SaintDavidSt,Saint David St,,-45.861586458263695,170.51067056224784,,\n" +
                "CumberlandSt,Cumberland St,,-45.861312841845766,170.5144046264356,,\n" +
                "LeithSt,Leith St,,-45.862251308203206,170.51771123241795,,\n" +
                "HarbourTceNorth,Harbour Tce North,,-45.86318211325738,170.5210308486017,,\n" +
                "HarbourTceSouth,Harbour Tce South,,-45.86672665424996,170.51946924716077,,\n" +
                "UnionStEast,Union St East,,-45.86627608204895,170.5163682736656,,\n" +
                "HydeSt,Hyde St,,-45.86797765678833,170.5149536268917,,\n" +
                "EthelBenjaminPl,Ethel Benjamin Pl,,-45.867369301187736,170.51271060012095,,\n" +
                "AlbanyStWest,Albany St West,,-45.86659131871253,170.50784881123795,,\n" +
                "HanoverSt,Hanover St,,-45.87000092853923,170.50594173608363,,\n" +
                "SaintAndrewSt,Saint Andrew St,,-45.87183500893659,170.50486943872238,,\n" +
                "MorayPl,Moray Pl,,-45.87295553164374,170.50431399074984,,\n" +
                "CivicCentre,Civic Centre,,-45.87335274067601,170.50392866134644,,\n" +
                "WallStreet,Wall Street,,-45.87138672858609,170.5051469896113,,\n" +
                "Dowsons,Dowsons,,-45.869507135511796,170.50621749655573,,\n" +
                "KnoxChurch,Knox Church,,-45.866853641649186,170.50759457721674,,\n" +
                "OtagoMuseum,Otago Museum,,-45.86659504419964,170.5104280352706,,\n" +
                "UniversityCentralLibrary,University Central Library,,-45.86731606608386,170.5126589923241,,\n" +
                "ForthSt,Forth St,,-45.86728858255155,170.5173952699716,,\n" +
                "HarbourTce,Harbour Tce,,-45.86628976028469,170.51958735786167,,\n" +
                "DundasStEast,Dundas St East,,-45.86293375161769,170.5202847435631,,\n" +
                "ClydeSt,Clyde St,,-45.86244065714265,170.51827595713294,,\n" +
                "NorthGround,North Ground,,-45.86153623623574,170.51495545331483,,\n" +
                "DundasStWest,Dundas St West,,-45.86055313178624,170.51126365666383,,\n" +
                "LowerParkSt,Lower Park St,,-45.862461335911966,170.50993046119694,,\n" +
                "UpperParkSt,Upper Park St,,-45.86364882273311,170.50606440636975,,\n" +
                "CorrieSt,Corrie St,,-45.86524153555591,170.50422917471153,,\n" +
                "QueensDrive,Queens Drive,,-45.86296294486518,170.50522481201187,,\n" +
                "TolcarneAveUp,Tolcarne Ave Up,,-45.861268084659386,170.50498124109615,,\n" +
                "FalklandSt,Falkland St,,-45.86006043986055,170.50252568117497,,\n" +
                "UpperGrendonSt,Upper Grendon St,,-45.85911450331102,170.4978442085585,,\n" +
                "ClaremontSt,Claremont St,,-45.861766973394275,170.49566139688122,,\n" +
                "PacificSt,Pacific St,,-45.86364994495508,170.4943332694525,,\n" +
                "FifieldSt,Fifield St,,-45.86548811034288,170.49239426364096,,\n" +
                "Roslyn,Roslyn,,-45.867538079406415,170.48949536536156,,\n" +
                "UpperHighgateUp,Upper Highgate Up,,-45.86905144885596,170.48677976501978,,\n" +
                "120000,Reading Cinemas,,-46.412324952930554,168.34743000000003,,\n" +
                "120001,Library,,-46.413327026097775,168.34723376580314,,\n" +
                "129901,Tay Street,,-46.412959999999984,168.3534688677978,,\n" +
                "129902,Esk Street,,-46.41188000000002,168.3582364709473,,\n" +
                "129903,Deveron Street,,-46.41094874969542,168.35372280658817,,\n" +
                "129904,Deveron Street,,-46.40794533651909,168.35373813331137,,\n" +
                "129905,Gala Street,,-46.406427676093976,168.3529815474967,,\n" +
                "129906,Gala Street,,-46.40646973488706,168.34703527020292,,\n" +
                "129907,Leven Street,,-46.40832298595994,168.34560360818432,,\n" +
                "129908,Spey Street,,-46.40971,168.34704629493717,,\n" +
                "129701,Spey Street,,-46.40969260359982,168.3500359648815,,\n" +
                "129702,Don Street,,-46.41079064655392,168.35666633553683,,\n" +
                "129703,Queens Drive,,-46.4100441384136,168.36142845378993,,\n" +
                "129704,Yarrow Street,,-46.40672138936269,168.37284684979318,,\n" +
                "129705,Isabella Street,,-46.40721825101114,168.37769064542,,\n" +
                "129706,Lithgow Street,,-46.40607983251474,168.38670946838943,,\n" +
                "129707,Glengarry Cres,,-46.40238090524156,168.38637910906345,,\n" +
                "129708,Lyon Street,,-46.400416489473976,168.3824834153387,,\n" +
                "129709,Exmouth Street,,-46.39934729771873,168.3756445800277,,\n" +
                "129710,George Street,,-46.396570000000004,168.37046377212505,,\n" +
                "129711,Windsor Street,,-46.39617861345865,168.36557459503751,,\n" +
                "129712,Herbert Street,,-46.39477670142994,168.3638770799032,,\n" +
                "129713,Queens Drive,,-46.388084231203926,168.36147000000005,,\n" +
                "129714,Kildare Drive,,-46.37763382287376,168.35969,,\n" +
                "129715,Bainfield Road,,-46.37562554535513,168.35756819676362,,\n" +
                "129716,Myers Street,,-46.37687882921942,168.35618999999997,,\n" +
                "129717,Cargill Street,,-46.37751078803592,168.3525337430301,,\n" +
                "129718,Bruce Street,,-46.378260000000026,168.3494352534483,,\n" +
                "129719,Durham Street,,-46.375240000000005,168.3447360232542,,\n" +
                "129720,Gloucester Street,,-46.37556123418709,168.34156487107543,,\n" +
                "129721,Ross Street,,-46.38092014422245,168.34268999999995,,\n" +
                "129722,Heywood Street,,-46.383610000000004,168.34012262374858,,\n" +
                "129723,Paterson Street,,-46.3858195024252,168.33701415644123,,\n" +
                "129724,Bay Road,,-46.38849488192573,168.33898102395938,,\n" +
                "129725,Dee Street,,-46.401995267836824,168.34735,,\n" +
                "129801,Tay Street,,-46.412959999999984,168.35312310577388,,\n" +
                "129802,Elles Road,,-46.41474444989478,168.36150999999995,,\n" +
                "129803,Tweed Street,,-46.41779655308553,168.3643865136114,,\n" +
                "129804,Millar Street,,-46.41695838956725,168.37155613560628,,\n" +
                "129805,Conyers Street,,-46.42097306498432,168.3746830117185,,\n" +
                "129806,Centre Street,,-46.422544902703066,168.37769241082253,,\n" +
                "129807,Lime Street,,-46.4210745867182,168.38270862383638,,\n" +
                "129808,Dome Street,,-46.41898607363228,168.385741328937,,\n" +
                "129809,Ash Street,,-46.42045999999998,168.39269105316157,,\n" +
                "129810,Thornhill Street,,-46.421911984488005,168.39430571800312,,\n" +
                "129811,Centre Street,,-46.42253689620298,168.3902245316067,,\n" +
                "129812,Regent Street,,-46.424995410578035,168.38814631492585,,\n" +
                "129813,John Street,,-46.42706,168.3801823094177,,\n" +
                "129814,Monowai Street,,-46.428146210218785,168.37348828316942,,\n" +
                "129815,Saturn Street,,-46.43012226162205,168.37210851263058,,\n" +
                "129816,Brown Street,,-46.43552018269076,168.37153948169885,,\n" +
                "129817,Bain Street,,-46.44220396021896,168.37338999999997,,\n" +
                "129818,Oreti Street,,-46.44391000000002,168.3722215130615,,\n" +
                "129819,Waiau Cres,,-46.44514999999999,168.36428217437742,,\n" +
                "129820,Southland Hospital,,-46.438069999999996,168.35889629867552,,\n" +
                "129821,Kew Road,,-46.43684999999999,168.36001999999996,,\n" +
                "129822,Elles Road,,-46.43244789514886,168.36147000000005,,\n" +
                "129823,Janet Street,,-46.42816482574127,168.35850989157098,,\n" +
                "129824,Conon Street,,-46.423664195139715,168.35374000000002,,\n" +
                "129825,Tweed Street,,-46.417989999999975,168.35018448379515,,\n" +
                "129826,Clyde Street,,-46.41555800900045,168.34722,,\n" +
                "990001,Invercargill Visitor Centre,,-46.406209592002455,168.35396040800026,,\n" +
                "990002,Five Rivers,,-45.623523,168.458592,,\n" +
                "990003,Queenstown Station Building,,-45.031004134622584,168.66032125876927,,\n" +
                "990010,Woodlands,,-46.35548697202335,168.55049268312166,,\n" +
                "990011,Edendale,,-46.31143581694165,168.78332978899925,,\n" +
                "990012,Mataura,,-46.19506194128278,168.86377748694804,,\n" +
                "990013,Gore,,-46.097914359642374,168.94549630787355,,\n" +
                "990014,Pukerau,,-46.09599632213995,169.0983504700041,,\n" +
                "990015,Clinton,,-46.20180076738067,169.37976808652002,,\n" +
                "990016,Balclutha,,-46.23697604708217,169.74339249467084,,\n" +
                "990017,Milton,,-46.121775199969846,169.95821453460394,,\n" +
                "990018,Waihola,,-46.02351277464943,170.0948655167432,,\n" +
                "990019,Dunedin - Airport,,-45.92211012848324,170.20272480728465,,\n" +
                "990020,Mosgiel Railway Station,,-45.887244794082136,170.34979842663324,,\n" +
                "990021,Dunedin - Central,,-45.88438000000001,170.49780999999996,,\n" +
                "990022,Dunedin - Otago University,,-45.86341523618221,170.51299936333714,,\n" +
                "990031,Te Anau,,-45.42053719406514,167.71482380515295,,\n" +
                "990030,Mossburn,,-45.66891071089604,168.23694655584575,,";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(getFullStopId()).append(',').append(formatGtfsString(stopName)).append(',').append(formatGtfsString(stopDesc))
                                  .append(',').append(stopLat).append(',').append(stopLon).append(',').append("").append(',').toString();
    }

    @Override
    public String getUniqueId() {
        return getStopId();
    }

    @Override
    public boolean isInclude() {
        if (route != null) return route.isInclude();
        return true;
    }

    @Override
    public String getDisplayName() {
        return toString();
    }

    private String formatGtfsString(String string) {
        if (string == null) return "";
        return string.replace(',', ';');
    }
}
