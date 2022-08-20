package com.example.volunteer.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ICPController {
    @GetMapping("/")
    @ResponseBody
    public String getMainPage(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>你&trade;劈我瓜</title>\n" +
                "</head>\n" +
                "<style>\n" +
                "    div.ICP{\n" +
                "        text-align: center;\n" +
                "    }\n" +
                "</style>\n" +
                "<body vlink=\"green\">\n" +
                "    <!--“title”就是鼠标悬停文本\n" +
                "    如果不写target=”_blank”那么就是在相同的标签页打开，如果写了target=”_blank”，就是在新的空白标签页中打开-->\n" +
                "    <h1>华强买瓜</h1>\n" +
                "    <p align=center>有一个人前来买瓜</p><!--这里飘红的原因是现在html最好只作为标记性语言，\n" +
                "    这类涉及布局的应当放入css中，还有就是不要用 p 标签去包 h！除了文本、图片和表单元素，\n" +
                "    其它的一律不能放，否则 p 标签会被自动处理掉-->\n" +
                "    <hr size=\"8\" color=\"red\" noshade=\"\"/>\n" +
                "    <!--又是一个单边标记，是“horizontal rule”的缩写，水平分割-->\n" +
                "    <p>生异形啊你哥几、哥俩<br />哥们儿，你这瓜多少钱一斤啊</p>\n" +
                "    <p>两块钱一斤</p>\n" +
                "    <div style=\"text-align: center;\">\n" +
                "        华强<img src=\"whatsup.jpg\" title=\"what's up\" align=\"center\"/>震惊\n" +
                "        <center>\"what's up\"</center><!--老规矩，不推荐使用的标签，在这个标签内任何对象都会居中-->\n" +
                "        <p>这瓜皮子是金子做的还是瓜粒子是金子做的？</p>\n" +
                "    </div>\n" +
                "    <!--div上可以附加class属性，div负责分块，css负责样式，我们将这种模式称为“div+css”-->\n" +
                "    <div>\n" +
                "        <span>\n" +
                "            （吐了一口烟，同时发出象鸣）你瞧瞧这现在哪有瓜呀？这都是大棚大鹏的瓜，\n" +
                "        </span>\n" +
                "        <span>\n" +
                "            你嫌贵（贤惠）我还嫌贵（贤惠）呢。\n" +
                "        </span><!--span也是一个文本级别的标签，因此与 p 标签一样\n" +
                "        除了文本、图片和表单元素，其它的一律不能放-->\n" +
                "    </div>\n" +
                "    <p><b>（微笑）给我挑一个</b></p>\n" +
                "    <pre>\n" +
                "        行。\n" +
                "\n" +
                "（摊主挑了一个瓜拍了两下（试图安抚西瓜情绪））\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    </pre><!--预格式化标签，保留内部所有换行符空白符，原封不动输出（一般在项目中用不着）-->\n" +
                "    <a href=\"#tosj\">返回顶部</a><!--锚链接，给超链接起一个名字，\n" +
                "        作用是在本页面或者其他页面的的不同位置进行跳转，但是要注意目标一定要也是超链接，不然无法跳转-->\n" +
                "    <div class=\"ICP\"><a href=\"https://beian.miit.gov.cn\" target=\"_blank\">蜀ICP备2022016295号</a></div>\n" +
                "</body>\n" +
                "</html>";
    }
}
