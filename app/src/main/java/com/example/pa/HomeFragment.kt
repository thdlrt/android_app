package com.example.pa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {
    var newsList:ArrayList<News> = ArrayList()
    lateinit var view_t:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_t=view
        //点击绑定
        val editText:EditText = view.findViewById(R.id.editText2)
        editText.setOnClickListener {
            val intent = Intent(getActivity(), SearchActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        refresh()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
    fun refresh(){
        val dbHelper: DatabaseHelper = DatabaseHelper.getInstance(requireActivity())
        newsList = dbHelper.getAllNews()
        //装载新闻
        if(newsList.size==0){
            //初始化数据库
            dbHelper.insertNews(News(0,"android.resource://${requireActivity().packageName}/${R.raw.news_1}","android.resource://${requireActivity().packageName}/${R.raw.news_1}","泽连斯基称俄袭击乌国家安全局一办公楼，俄媒：袭击已致数十名军官死亡","【环球网报道】据乌克兰“RBC.UA”新闻网报道，乌克兰总统泽连斯基当地时间28日在社交平台Telegram上发文称，俄军当天对乌克兰第聂伯罗彼得罗夫斯克州发动导弹袭击，该州一栋高层建筑和乌国家安全局一栋办公楼遭袭。\n" +
                    "\n" +
                    "报道称，泽连斯基当天还在Telegram上发文称，对于上述袭击事件，他已与乌国家安全局、内务部、紧急情况部门以及第聂伯罗彼得罗夫斯克州军事行政管理部门负责人谢尔盖·雷萨科交谈，有关部门正在现场开展工作。泽连斯基还称，“我们将尽全力让俄罗斯因其侵略行为和针对我们民众的恐怖主义行为得到惩罚”。"
            ,R.drawable.ic_hot,"热门","环球网"))
            dbHelper.insertNews(News(0,"android.resource://${requireActivity().packageName}/${R.raw.news_2_1}","android.resource://${requireActivity().packageName}/${R.raw.news_2_2}",
                    "齐齐哈尔市第三十四中学校发布悼文","",R.drawable.ic_hot,"热门","界面新闻"))
            dbHelper.insertNews(News(0,"android.resource://${requireActivity().packageName}/${R.raw.news_3}","android.resource://${requireActivity().packageName}/${R.raw.news_3}","《罗刹海市》火了，刀郎经纪方：以后不会回应",
            "台风“杜苏芮”正在现实中影响人们的生活，刀郎新歌《罗刹海市》就像娱乐圈中的台风，自诞生十天来风暴愈刮愈烈。纵使网友如何猜测，当事人刀郎，以及网友猜测遭影射的几位歌手都如稳居台风眼一般沉寂如常。\n" +
                    "\n" +
                    "连日来，顶端新闻记者希望联系刀郎方面，试图让他阐述创作动机，以及回应网友议论，7月29日，刀郎经纪方表示，关于《罗刹海市》以后不会有公开回应。\n" +
                    "\n" +
                    "刀郎本名罗林，出生于1971年，2004年1月，专辑《2022年的第一场雪》在没有任何宣传的情况下，从新疆火遍全国，除了专辑同名主打歌，《情人》《冲动的惩罚》等歌也成为家喻户晓的大众歌曲，这张专辑最终以270万张的销量登顶华语乐坛榜首。"
            ,R.drawable.ic_hot,"热门","长江云新闻"))
            dbHelper.insertNews(News(0,null,null,"时政新闻眼丨习近平赴四川陕西考察，足迹之中饱含深意","今年5月，习近平总书记在西安主持中国－中亚峰会前夕，专门听取陕西省委和省政府工作汇报，并在前往陕西途中到山西运城考察。\n" +
                    "\n" + "2个多月后，总书记再次展开跨省行程：在出席成都大运会开幕式前夕，到四川考察；在开幕式后返京途中，到陕西汉中考察。\n" + "\n" + "这次不同寻常的跨省考察，总书记关注了什么，强调了什么？《时政新闻眼》为你解读。" + "这次川陕之行，从7月25日开始，跨越到7月29日。习近平总书记在四川、陕西各看了两个考察点，并在成都出席了一场汇报会。\n" +
                    "\n" + "四个考察点，分别是四川广元的古蜀道翠云廊、四川德阳的三星堆遗址、陕西汉中的汉中市博物馆和天汉湿地公园。"+"在7月27日的四川省委和省政府工作汇报会上，习近平总书记还谈到当前要重点做好的两件大事。\n" + "\n" + "一是防灾减灾救灾。总书记说，7、8月份长江流域进入主汛期，要全面落实防汛救灾主体责任，做好防汛抗洪救灾各项应对准备工作。\n" +
                    "\n" + "二是主题教育。总书记说，第一批主题教育只剩下一个多月时间，各级党组织要落实党中央部署，善始善终、慎终如始，务求实效。\n" + "\n" + "以“时时放心不下”的责任感切实保障人民生命财产安全，以“钉钉子精神”抓好主题教育这件事关全局的大事，以实干笃行向世界展示中国式现代化的万千气象。时不我待，行动起来"
                ,R.drawable.ic_top,"时政","央视新闻"))
            dbHelper.insertNews(News(0,null,null,"人民网评：全力应对“杜苏芮”，把灾害损失降到最低","7月28日上午，超强台风“杜苏芮”已于福建晋江沿海登陆。“杜苏芮”是今年来登陆我国的最强台风及有完整观测记录以来登陆福建第二强的台风，具有强度大、水汽量足、影响范围广、持续时间长等特点。受其影响，福建沿海地区出现狂风暴雨，在台风登陆地及相邻区域，有人员受伤，有树木被刮倒，一些地方出现停电，威力不容小觑。\n" +
                    "\n" +
                    "台风是一种破坏力很强的灾害性天气系统，台风过境易损坏建筑物、车辆等，影响正常生产作业，威胁群众生命安全。与此同时，当前我国正值防汛关键期，当汛期叠加台风，可能引发城乡积涝、局地山洪、山体滑坡及老屋坍塌等灾害。所以，防范应对台风，事关人民群众生命财产安全，事关经济发展和社会稳定。灾害不可避免，关键在于将损失降到最低。各地各部门树牢底线思维、极限思维，坚持人民至上、生命至上，定能打好防范应对台风主动仗。\n" +
                    "\n" +
                    "应对台风，必须将日常保障与应急处置结合起来。面对已经到来的台风，基本民生保障和生活服务至关重要。有关部门应从广大群众切身利益出发，在及时转移危险地区群众的前提下，加强交通、旅游和城市运行安全管理，切实做好供水、供电、通讯、交通、医疗和生活物资保障，确保受灾群众有饭吃、有房住、能看病。对于随时可能出现的次生灾害，应强化抢险救援准备，集结消防、电力、水利、交通等部门救援力量并建立联动机制，随时待命、有令即动，第一时间做好应急抢修、避险群众安置等工作，尽最大努力防止人员伤亡。\n" +
                    "\n" +
                    "防范台风，必须将抓全面与抓重点结合起来。目前来看，“杜苏芮”可能影响黄淮、华北、西南地区东部、西北地区东部等地区。各地必须以“一失万无”的忧患意识将安全防护网织得更牢更密。要看到，防范台风工作千头万绪，涉及气象预警、风险排查、信息发布、人员调度等方方面面，必须全面细致部署、环环相扣落实。对于重点部位、薄弱环节应加大关注力度，及时堵塞漏洞。例如，抓好危化品、燃气等重点行业领域安全生产，对农林渔业等遭受灾害风险高的行业强化灾害防御、及时撤回工作人员，对隧道、地铁、下沉式立交桥等易涝点加强巡防，等等。\n" +
                    "\n" +
                    "防范应对台风是一场大战大考，考验着相关部门的响应能力，考验着党员干部的担当作为。水利、住建、公安、交通运输等方面各司其职、有序衔接，军地加强联动，才能凝聚防范应对台风的最强合力。广大党员干部应绷紧思想之弦，把形势估计得更复杂一些，把问题考虑得更充分一些，立足最不利的情况做最充分的准备；应发挥先锋模范作用，哪里灾情严重就战斗在哪里，哪里群众需要就出现在哪里，全力以赴保障人民群众生命财产安全。\n" +
                    "\n" +
                    "为防御台风“杜苏芮”，各地严阵以待，有的停产、停课、停业、休市，有的停运列车、公交车，有的部署救助船、救助直升机。但也要看到，每个人都是自己安全的第一责任人。增强防风防汛意识，密切关注气象部门发布的信息，做好防范工作，远离危险场所，做好物资储备，相信我们定能安全度过此次台风。"
                ,R.drawable.ic_top,"时政","人民网"))
        }
        val recyclerView: RecyclerView = view_t.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = NewsAdapter(newsList,view_t.getContext())
        recyclerView.adapter = adapter
    }
}