package com.ehc.ElasticSearchImageSearch.util;

import com.ehc.elastiknnSimilarityQuery.KnnQueryBuilder;
import com.ehc.elastiknnSimilarityQuery.Similarity;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ImageToVectorTest {


    @Autowired
    ImageToVector imageToVector;

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient lowClient;

    @Test
    void 이미지_url_vector화_테스트()
    {
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String vector=imageToVector.imageUrlToVector(imageUrl);
        if(!vector.equals("")&&vector!=null)
        {
            System.out.println("image vector화 성공");
            System.out.println(vector);
        }
        else
        {
            System.out.println("image vector화 실패");
        }
    }


    @Test
    void 서치빌드연동테스트() throws IOException {
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String vector=imageToVector.imageUrlToVector(imageUrl);
        String vectorForQuery="";
        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");
        Matcher matcher= pattern.matcher(vector);
        while (matcher.find()) {
            vectorForQuery=matcher.group(2);
            if(matcher.group(1) ==  null)
                break;
        }
        String[] vectors=vectorForQuery.split(",");
        SearchRequest searchRequest= new SearchRequest("test-image-vector-angular");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.COSINE,vectors);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.query(knnQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse= client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits=searchResponse.getHits();
        List<Map<String,Object>> results=new ArrayList<>();
        for(SearchHit hit:searchHits)
        {
            Map<String,Object> result= hit.getSourceAsMap();
            results.add(result);
        }
        System.out.println("검색결과");
        System.out.println("총"+searchHits.getTotalHits()+"개");
        System.out.println(results.size()+"가 검색되었음");
        for (Map<String,Object> result:results)
        {
            System.out.println(result);
        }

    }

    @Test
    void exact쿼리_테스트() throws IOException {
        double[] doubleVector= {0.4706926941871643, 1.745194435119629, 1.5319931507110596, 0.35290220379829407, 0.004411882720887661, 1.2420624494552612, 0.0, 0.1843896359205246, 0.4779375493526459, 0.08604198694229126, 1.027640700340271, 0.7219198346138, 0.3156262934207916, 0.1532789021730423, 0.897074818611145, 0.10074511170387268, 3.8352742195129395, 1.9829137325286865, 0.821117639541626, 0.07275685667991638, 1.248317837715149, 0.32938703894615173, 1.2206082344055176, 0.5920532941818237, 2.2589731216430664, 0.0782669335603714, 0.6229475140571594, 0.4856052100658417, 0.8217656016349792, 0.16048850119113922, 0.5066311955451965, 0.6047220230102539, 0.7937353253364563, 0.0992950052022934, 0.2774549126625061, 0.06802964210510254, 0.11989209055900574, 2.99052095413208, 0.5300158262252808, 0.7141923904418945, 0.4926600456237793, 0.35296809673309326, 0.8539013862609863, 0.05355224385857582, 0.8167543411254883, 1.1017135381698608, 0.08065050840377808, 0.9688655138015747, 0.28566011786460876, 2.92586088180542, 0.581080436706543, 0.536986231803894, 1.1268116235733032, 0.4608815908432007, 1.3603554964065552, 0.886022686958313, 0.07599061727523804, 0.8159232139587402, 0.5875003337860107, 1.5085657835006714, 2.285186529159546, 0.020173940807580948, 0.03829992562532425, 0.08010375499725342, 0.08369559049606323, 0.3877682089805603, 0.9103416204452515, 0.8401895761489868, 1.0163480043411255, 0.10469907522201538, 0.6653838753700256, 0.31966710090637207, 1.5195461511611938, 0.3367636501789093, 0.9087916612625122, 0.09152926504611969, 0.10574287176132202, 1.388491153717041, 1.3484619855880737, 0.6925022602081299, 1.0692909955978394, 0.1015586256980896, 0.6923312544822693, 0.4506777226924896, 1.1871058940887451, 0.9461762309074402, 1.4874930381774902, 3.7480955123901367, 1.5477279424667358, 0.20386913418769836, 1.6340937614440918, 0.36831846833229065, 2.1037404537200928, 1.5872048139572144, 0.2507072687149048, 0.8045300841331482, 1.4804587364196777, 1.6464687585830688, 0.8094217777252197, 0.942232072353363, 1.1503894329071045, 2.242422342300415, 0.1184660792350769, 2.533977508544922, 0.0, 0.11007338017225266, 0.1374567151069641, 2.389486074447632, 0.048802975565195084, 0.3505496084690094, 0.40850305557250977, 0.7524229288101196, 0.21721650660037994, 2.6865739822387695, 0.6297368407249451, 0.8293471932411194, 1.0038495063781738, 0.12786324322223663, 0.6100688576698303, 0.867310106754303, 0.520078182220459, 0.47578904032707214, 0.3158564269542694, 0.879585325717926, 0.17539028823375702, 0.8758814334869385, 0.36595800518989563, 2.318249225616455, 0.5468100905418396, 1.1212859153747559, 0.0, 0.13998064398765564, 0.5058508515357971, 0.8964375257492065, 0.027829477563500404, 2.252863883972168, 0.8749920725822449, 1.1428675651550293, 0.33097130060195923, 0.7598358988761902, 1.1965051889419556, 0.0722239762544632, 0.19872792065143585, 0.4123775064945221, 0.04173281788825989, 0.16670982539653778, 0.646691620349884, 0.29279825091362, 0.2902078330516815, 0.8504043817520142, 0.2286238819360733, 1.2600208520889282, 0.014675570651888847, 0.9432089924812317, 0.05782245099544525, 0.9168939590454102, 0.019053636118769646, 0.04463595896959305, 0.795805811882019, 0.5860180258750916, 0.9912945628166199, 0.6159332394599915, 0.6521369814872742, 0.36939123272895813, 0.20638321340084076, 0.18010343611240387, 0.2747966945171356, 0.6979463696479797, 1.194047451019287, 1.220396637916565, 0.3553932309150696, 1.3682432174682617, 0.6964280009269714, 0.04080212488770485, 0.0070949397049844265, 0.10067118704319, 1.638899803161621, 0.5666887164115906, 0.2165718823671341, 1.0382630825042725, 2.0780491828918457, 0.44407662749290466, 0.5747484564781189, 0.42329084873199463, 1.0409927368164062, 0.7973757982254028, 0.1162286102771759, 1.1458395719528198, 0.918976366519928, 0.46000605821609497, 0.3084257245063782, 1.2714306116104126, 0.8990325927734375, 0.5623444318771362, 1.611802101135254, 0.43257614970207214, 0.9557929635047913, 0.09356965869665146, 0.9856682419776917, 0.08834919333457947, 0.2339819073677063, 0.0, 0.6470345258712769, 0.32556527853012085, 0.21480825543403625, 0.11793294548988342, 0.6612573862075806, 1.367342233657837, 0.09573758393526077, 0.48990023136138916, 1.0471237897872925, 0.16752082109451294, 0.06939296424388885, 0.2791341245174408, 0.9588412046432495, 2.0631661415100098, 0.1168292760848999, 0.21324381232261658, 0.01044375915080309, 0.9913772940635681, 0.0801628977060318, 0.5446751713752747, 1.4246987104415894, 0.2539082169532776, 1.0740000009536743, 0.6875640153884888, 0.5073202252388, 1.1658108234405518, 0.16316209733486176, 1.1083271503448486, 0.09111413359642029, 1.2806942462921143, 0.04223698005080223, 3.113690137863159, 0.3935244083404541, 0.31067368388175964, 0.08197402209043503, 0.35927706956863403, 0.41248664259910583, 0.34849750995635986, 1.0051498413085938, 1.2094552516937256, 1.043076515197754, 0.18499939143657684, 1.0695598125457764, 0.6919949650764465, 0.7519742250442505, 0.5886467695236206, 1.1200895309448242, 0.06513144820928574, 0.35275089740753174, 1.1851513385772705, 0.25806692242622375, 0.49892550706863403, 0.0, 0.3536587655544281, 0.7485927939414978, 2.825627565383911, 0.3321608006954193, 1.7620242834091187, 2.346982717514038, 0.45277783274650574, 0.6622896790504456, 0.4333183467388153, 0.0919637382030487, 1.2796225547790527, 0.516093909740448, 1.3356802463531494, 0.2554571032524109, 0.8483647108078003, 0.6743387579917908, 0.6401651501655579, 0.19288016855716705, 0.4409162104129791, 1.2635236978530884, 0.8661911487579346, 0.635455310344696, 0.36926764249801636, 0.26123738288879395, 0.8351659178733826, 1.5222538709640503, 1.7339377403259277, 0.3417098820209503, 1.6187214851379395, 1.8209296464920044, 0.24540679156780243, 0.44203683733940125, 1.47694993019104, 0.11054760217666626, 0.2529240846633911, 0.4476683437824249, 0.2336215376853943, 1.763075828552246, 0.06907862424850464, 0.6747536659240723, 0.5567185282707214, 0.47383058071136475, 0.2458718717098236, 0.8274184465408325, 0.6086921095848083, 0.12304417788982391, 0.31861403584480286, 0.2726757526397705, 0.749088704586029, 0.34303027391433716, 1.8277937173843384, 5.61077962629497E-4, 0.42433443665504456, 0.392819344997406, 0.7362760305404663, 0.3636796176433563, 1.8231052160263062, 1.1841500997543335, 0.744762122631073, 0.6095240712165833, 0.2621445953845978, 2.2105836868286133, 0.21201668679714203, 0.2727360129356384, 0.7699036598205566, 1.2257639169692993, 0.7835691571235657, 1.9049021005630493, 0.30001503229141235, 1.0185575485229492, 1.0425341129302979, 0.11346495151519775, 0.01491473987698555, 0.35624417662620544, 0.38820651173591614, 1.898883581161499, 3.1757652759552, 2.436732292175293, 0.33857226371765137, 0.2773592472076416, 0.4176253378391266, 0.2871207296848297, 0.14971067011356354, 2.1595897674560547, 0.380261093378067, 0.48713070154190063, 0.7250391244888306, 1.279180645942688, 0.7755195498466492, 0.40347909927368164, 1.0911098718643188, 1.2441116571426392, 0.7202754020690918, 0.3472580313682556, 0.07985179871320724, 0.9298306703567505, 1.021920919418335, 0.2784537971019745, 0.48857608437538147, 0.6532874703407288, 1.8274965286254883, 1.2511993646621704, 0.008400148712098598, 0.7643728256225586, 0.5873085856437683, 1.542173147201538, 0.4014510214328766, 0.2275862991809845, 0.024336935952305794, 0.23774686455726624, 1.7925519943237305, 0.16380253434181213, 1.205404281616211, 2.074694871902466, 0.0037285180296748877, 0.13535240292549133, 0.9397243857383728, 2.6786978244781494, 1.4890859127044678, 0.3094051480293274, 0.6192693710327148, 1.2834113836288452, 1.1296300888061523, 1.677746057510376, 0.4081049859523773, 1.7567667961120605, 1.0938760042190552, 0.4004625082015991, 1.445050597190857, 1.2539052963256836, 0.9772065877914429, 0.9276479482650757, 0.6138421297073364, 0.477664053440094, 1.281947374343872, 0.47680163383483887, 0.0, 0.40495923161506653, 1.0364700555801392, 1.6318135261535645, 0.32288217544555664, 1.0847266912460327, 0.9229565262794495, 0.15673711895942688, 0.7320713400840759, 1.2637722492218018, 0.06603053212165833, 0.31815212965011597, 0.2743695378303528, 1.2307132482528687, 0.480734646320343, 0.18839018046855927, 0.0, 1.1907167434692383, 0.0028679503593593836, 0.5710466504096985, 1.1864780187606812, 0.9278241991996765, 0.6060863137245178, 0.0, 0.9837425351142883, 0.143755704164505, 0.7010935544967651, 1.0371627807617188, 0.7019000053405762, 2.5776400566101074, 0.4646315276622772, 0.08123289048671722, 0.8232259154319763, 0.11563393473625183, 0.082536481320858, 0.012129698880016804, 1.553633689880371, 1.5992051362991333, 0.0, 0.1767539530992508, 0.08908981084823608, 3.5000433921813965, 0.23765036463737488, 0.8282143473625183, 0.04527012258768082, 0.18010298907756805, 1.5538564920425415, 1.3121347427368164, 1.3371753692626953, 3.1214945316314697, 0.43121352791786194, 0.18834814429283142, 0.5331762433052063, 1.3972212076187134, 0.5420868396759033, 1.2813842296600342, 0.15240129828453064, 0.6273752450942993, 0.6144697070121765, 0.11408703029155731, 1.80064857006073, 1.2112077474594116, 0.6216450333595276, 2.1117520332336426, 0.2621594965457916, 0.19031557440757751, 0.11028266698122025, 3.126955509185791, 0.7790092825889587, 1.6019680500030518, 1.7136958837509155, 0.0, 0.4208306670188904, 0.3865993916988373, 0.9343750476837158, 0.9126307964324951, 0.49303537607192993, 0.4549131691455841, 0.02294350229203701, 0.2903554141521454, 0.3915596306324005, 0.49690258502960205, 1.2033199071884155, 2.6280195713043213, 0.30618712306022644, 0.25307875871658325, 0.23189568519592285, 0.24468302726745605, 0.33342623710632324, 0.7481374740600586, 0.19438201189041138, 0.18774549663066864, 0.16860166192054749, 1.5675020217895508, 0.11013231426477432, 0.2724098861217499, 0.062324512749910355, 1.3271307945251465, 0.11571566760540009, 0.6056123971939087, 0.9220062494277954, 0.003704608418047428, 1.1274110078811646, 0.6221882104873657, 0.176106259226799, 0.6278066635131836, 0.102107934653759, 2.861884593963623, 0.4050561189651489, 0.5042702555656433, 0.7974761724472046, 0.5714097619056702, 0.9966986775398254, 0.735877275466919, 0.3073747158050537, 0.47576141357421875, 0.15566496551036835, 0.6162214279174805, 0.7050394415855408, 1.9756580591201782, 0.12724696099758148};
        int size = doubleVector.length;
        String[] vectors = new String[size];
        for(int i=0; i<size; i++) {
            vectors[i] = String.valueOf(doubleVector[i]);
        }
        SearchRequest searchRequest= new SearchRequest("test-image-vector-angular");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.EXACT,vectors);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.query(knnQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse= client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits=searchResponse.getHits();
        List<Map<String,Object>> results=new ArrayList<>();
        for(SearchHit hit:searchHits)
        {
            Map<String,Object> result= hit.getSourceAsMap();
            results.add(result);
        }
        System.out.println("검색결과");
        System.out.println("총"+searchHits.getTotalHits()+"개");
        System.out.println(results.size()+"가 검색되었음");
        for (Map<String,Object> result:results)
        {
            System.out.println(result);
        }

    }



}