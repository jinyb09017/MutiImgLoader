package com.abbott.mutiimageloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.abbott.mutiimgloader.qq.QqMerge;
import com.abbott.mutiimgloader.util.JImageLoader;
import com.abbott.mutiimgloader.weixin.WeixinMerge;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ImageView iv_weixin;
    ImageView iv_weixin1;
    ImageView iv_weixin2;
    ImageView iv_qq;
    ImageView iv_qq1;
    ImageView iv_qq2;

    static List<String> urls = new ArrayList<>();

    static {
        urls.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1505294718&di=b6934dd570c0c6962a8dbbb12eac27f5&src=http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201204/20120412123914329.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505304803685&di=3a859c35334d8d86177f0cfb8adfdc65&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120412123926750.jpg");
        urls.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1505307059&di=f0681cbe5ff7604b9cd62bdb7b071e6c&src=http://b.zol-img.com.cn/sjbizhi/images/2/750x530/1354868342195.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145477&di=ebea82181a485571bddea5ad41b0b841&imgtype=0&src=http%3A%2F%2Fi3.sinaimg.cn%2Fgm%2F2011%2F1103%2FU4511P115DT20111103112522.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145477&di=26e3b5ab30a9fdcc0b077bc9eea340e1&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Fmw600%2F6b146538jw1dztjeivhpuj.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145476&di=9dfc96ab94f153e49cb2db2da09a97cf&imgtype=0&src=http%3A%2F%2Fwww.discuz.images.zq.sd.cn%2FDiscuz%2Fforum%2F201308%2F30%2F193725qm9qwzz7qidmqqmq.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145476&di=fa19d31e36fd331242a014910a13669a&imgtype=0&src=http%3A%2F%2F9.pic.pc6.com%2Fthumb%2Fup%2F2014-6%2F14019543202414731_600_0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317401448&di=dc4492aeeb496dc73880c63c439186d6&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D877642269%2C2202333197%26fm%3D214%26gp%3D0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145471&di=4f9b528159a635467f39746b91139829&imgtype=0&src=http%3A%2F%2Fwww.16sucai.com%2Fuploadfile%2F2011%2F1009%2F20111009041805525.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145471&di=086a4eed6ca7f934fe1c3765940cf867&imgtype=0&src=http%3A%2F%2Fb.zol-img.com.cn%2Fsjbizhi%2Fimages%2F6%2F320x510%2F1382519980823.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145471&di=a6fc4073f44d99befc39fe3c30be688f&imgtype=0&src=http%3A%2F%2Fb.zol-img.com.cn%2Fsjbizhi%2Fimages%2F6%2F320x510%2F1395393066343.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145471&di=35cbde9ae1b124fedbdaa96a258297fb&imgtype=0&src=http%3A%2F%2Fb.zol-img.com.cn%2Fsjbizhi%2Fimages%2F5%2F320x510%2F1372754988391.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145470&di=19d439fa1a302aef8fd2fcadfbca771c&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Fmw600%2F6b146538jw1dztje3wasfj.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317523533&di=a1fef3d8ef1dcbd564c9e59d6ac6c3d5&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D2277168614%2C4145779101%26fm%3D214%26gp%3D0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145469&di=e9d63be606584c29bc5bc8abf109dc9d&imgtype=0&src=http%3A%2F%2Fs4.sinaimg.cn%2Fmw690%2F609f8b00gd5ee83e31233%26690");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317488354&di=996c983ba9cd30bf04b6bd684b0167ce&imgtype=0&src=http%3A%2F%2Fs6.sinaimg.cn%2Fmiddle%2F10bc65e44c8d713456d55%26960");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_weixin = (ImageView) findViewById(R.id.iv_weixin);
        iv_weixin1 = (ImageView) findViewById(R.id.iv_weixin1);
        iv_weixin2 = (ImageView) findViewById(R.id.iv_weixin2);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_qq1 = (ImageView) findViewById(R.id.iv_qq1);
        iv_qq2 = (ImageView) findViewById(R.id.iv_qq2);

        JImageLoader imageLoader = new JImageLoader(this);
        imageLoader.configDefaultPic(R.drawable.ic_launcher_round);

        List<String> urls2 = new ArrayList<>();
        urls2.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317523533&di=a1fef3d8ef1dcbd564c9e59d6ac6c3d5&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D2277168614%2C4145779101%26fm%3D214%26gp%3D0.jpg");
        urls2.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145469&di=e9d63be606584c29bc5bc8abf109dc9d&imgtype=0&src=http%3A%2F%2Fs4.sinaimg.cn%2Fmw690%2F609f8b00gd5ee83e31233%26690");


        List<String> urls3 = new ArrayList<>();
        urls3.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317401448&di=dc4492aeeb496dc73880c63c439186d6&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D877642269%2C2202333197%26fm%3D214%26gp%3D0.jpg");
        urls3.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145471&di=4f9b528159a635467f39746b91139829&imgtype=0&src=http%3A%2F%2Fwww.16sucai.com%2Fuploadfile%2F2011%2F1009%2F20111009041805525.jpg");
        urls3.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145471&di=086a4eed6ca7f934fe1c3765940cf867&imgtype=0&src=http%3A%2F%2Fb.zol-img.com.cn%2Fsjbizhi%2Fimages%2F6%2F320x510%2F1382519980823.jpg");
        urls3.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505317145471&di=a6fc4073f44d99befc39fe3c30be688f&imgtype=0&src=http%3A%2F%2Fb.zol-img.com.cn%2Fsjbizhi%2Fimages%2F6%2F320x510%2F1395393066343.jpg");


        imageLoader.displayImages(urls, iv_weixin, new WeixinMerge());
        imageLoader.displayImages(urls2, iv_weixin1, new WeixinMerge());
        imageLoader.displayImages(urls3, iv_weixin2, new WeixinMerge());
        imageLoader.displayImages(urls, iv_qq, new QqMerge());
        imageLoader.displayImages(urls2, iv_qq1, new QqMerge());
        imageLoader.displayImages(urls3, iv_qq2, new QqMerge());

    }

    public void onBitmapBtnWX(View view) {
        startActivity(new Intent(this, RecyclerActivity.class).putExtra("tag", 1));
    }

    public void onBitmapBtnQQ(View view) {

        startActivity(new Intent(this, RecyclerActivity.class).putExtra("tag", 2));
    }
}
