package com.app.mybaseframwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConvertResult implements Serializable {

  /**
   * from : en
   * to : zh
   * trans_result : [{"src":"(8k,RAW photo,best quality,highres,masterpiece:1.2),absurdres,detailed face,1girl,exquisite facial features,blush,cowboy shot,braided bangs,shiny hair,ribbon trim,T-shirt,look at viewer,dungeon","dst":"（8k，RAW照片，最佳质量，高层，杰作：1.2），荒诞，细节脸，1girl，精致的五官，腮红，牛仔镜头，辫子刘海，闪亮的头发，缎带装饰，T恤，看着观众，地牢"}]
   */

  private String from;
  private String to;
  private List<TransResultBean> trans_result=new ArrayList<>();

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public List<TransResultBean> getTrans_result() {
    return trans_result;
  }

  public void setTrans_result(List<TransResultBean> trans_result) {
    this.trans_result = trans_result;
  }

  public static class TransResultBean implements Serializable {
    /**
     * src : (8k,RAW photo,best quality,highres,masterpiece:1.2),absurdres,detailed face,1girl,exquisite facial features,blush,cowboy shot,braided bangs,shiny hair,ribbon trim,T-shirt,look at viewer,dungeon
     * dst : （8k，RAW照片，最佳质量，高层，杰作：1.2），荒诞，细节脸，1girl，精致的五官，腮红，牛仔镜头，辫子刘海，闪亮的头发，缎带装饰，T恤，看着观众，地牢
     */

    private String src;
    private String dst;

    public String getSrc() {
      return src;
    }

    public void setSrc(String src) {
      this.src = src;
    }

    public String getDst() {
      return dst;
    }

    public void setDst(String dst) {
      this.dst = dst;
    }
  }
}
