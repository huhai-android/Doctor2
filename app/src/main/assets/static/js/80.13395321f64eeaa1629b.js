webpackJsonp([80],{Hf7z:function(n,e,t){var i=t("e99e");"string"==typeof i&&(i=[[n.i,i,""]]),i.locals&&(n.exports=i.locals);t("rjj0")("d8d3a40a",i,!0,{})},RcmT:function(n,e,t){"use strict";function i(n){t("Hf7z")}Object.defineProperty(e,"__esModule",{value:!0});var a=t("mvHQ"),r=t.n(a),o=t("pKZN"),d=(o.a,{components:{back:o.a},data:function(){return{title:"推荐详情",isRecommend:!1,recomMsg:null,info:null,totalPrice:null,showBtn:!1,patient:this.$store.state.patient}},created:function(){this.$store.state.recomMsg&&(this.recomMsg=this.$store.state.recomMsg),this.$route.query.fromIMForAPP&&(this.recomMsg={DataSource:this.$route.query.DataSource,DataId:this.$route.query.DataId}),this.recomMsg?this.pull():this.$toast("暂未选择推荐的商品！"),console.log(this.$store.state.inspctArea)},mounted:function(){},beforeDestroy:function(){window.onscroll=null},methods:{showPopup:function(){var n=this;if(!this.info)return!1;if(this.recomMsg&&this.recomMsg.fromIM&&this.patient&&this.patient.PatientId){var e=[];e.push(this.patient.PatientId),this.$post("/PlatFormAPI/SpecialistHosGroup/SendGoodsRecommend",{DataSource:this.recomMsg.DataSource,DataId:this.recomMsg.DataId,PatientIdList:e}).then(function(e){n.$bridge.callhandler("BackToIM","123")})}else this.recomMsg.LinkAddress=this.info.LinkAddress,this.$bridge.callhandler("SendGoodsRecommend",r()(this.recomMsg))},goEdit:function(){this.$store.dispatch("pushEditRecomDetails",this.info),this.$router.push("/prefRecommend/reDetailEdit")},changeRecom:function(){this.isRecommend=!this.isRecommend,this.$post("/PlatFormAPI/SpecialistHosGroup/SetGoodsRecommendUsed",{DataSource:this.recomMsg.DataSource,DataId:this.recomMsg.DataId,IsOn:this.isRecommend?1:0}).then(function(n){})},pull:function(){var n=this;this.$post("/PlatFormAPI/SpecialistHosGroup/GetGoodsRecommendInfo",{DataSource:this.recomMsg.DataSource,DataId:this.recomMsg.DataId}).then(function(e){if(e){n.showBtn=!0;var t=n.addTotal(e.OtherAmount,e.AllPrice);n.info=e,n.totalPrice=n.subTotal(t,e.CouponAmount)}})},addTotal:function(n,e){var t=Number(n),i=Number(e),a=0,r=0,o=0;try{a=t.toString().split(".")[1].length}catch(o){a=0}try{r=i.toString().split(".")[1].length}catch(o){r=0}return o=Math.pow(10,Math.max(a,r)),((t*o+i*o)/o).toFixed(2)},subTotal:function(n,e){var t=Number(n),i=Number(e),a=0,r=0,o=0;try{a=t.toString().split(".")[1].length}catch(n){a=0}try{r=i.toString().split(".")[1].length}catch(n){r=0}return o=Math.pow(10,Math.max(a,r)),((t*o-i*o)/o).toFixed(2)}}}),c=function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("div",{ref:"el",staticClass:"container pdt",attrs:{id:"re-detail"}},[t("div",{staticClass:"title"},[n.$route.query.fromIMForAPP?t("back",{attrs:{checkId:2}}):n._e(),n._v(" "),n.$route.query.fromIMForAPP||n.$route.query.tId?n._e():t("back",{attrs:{checkId:3}}),n._v(" "),n.$route.query.tId?t("back",{attrs:{checkId:3}}):n._e(),n._v(" "),t("span",{staticClass:"name"},[n._v(n._s(n.title))]),n._v(" "),n.info&&n.info.CanEdit&&!n.$route.query.fromIMForAPP&&!n.$route.query.tId?t("span",{staticClass:"edit",on:{click:function(e){n.goEdit()}}},[n._v("编辑")]):t("a")],1),n._v(" "),t("div",{staticClass:"qr-box"},[t("h4",[n._v("患者微信扫码支付推荐订单")]),n._v(" "),t("div",{staticClass:"tit"},[n._v("请患者用微信扫描二维码，或点击“发送推荐”将推荐商品订单发送给患者")]),n._v(" "),t("div",{staticClass:"qr-code"},[n.info&&n.info.CodeAddress?t("img",{attrs:{src:n.info.CodeAddress}}):t("i")])]),n._v(" "),n.info?t("div",{staticClass:"content"},n._l(n.info.ListDetails,function(e,i){return t("div",{key:i,staticClass:"item"},[t("p",{staticClass:"n"},[t("span",{staticClass:"l"},[n._v(n._s(e.GoodsName))]),n._v(" "),5==e.GoodsSource?t("span",{staticClass:"r"},[n._v("预约后收费")]):t("span",{staticClass:"r"},[n._v("￥"+n._s(e.OnePrice))])]),n._v(" "),2==e.GoodsSource||3==e.GoodsSource?t("p",{staticClass:"grey"},[t("span",{staticClass:"l"},[n._v(n._s(e.MerchantName))]),n._v(" "),t("span",{staticClass:"r"},[n._v("x"+n._s(e.GoodsNum))])]):n._e(),n._v(" "),1==e.GoodsSource||4==e.GoodsSource||7==e.GoodsSource?t("p",{staticClass:"grey"},[t("span",{staticClass:"l"},[n._v(n._s(e.HospitalName)+" "+n._s(e.DepartmentName))]),n._v(" "),t("span",{staticClass:"r"},[n._v("x"+n._s(e.GoodsNum))])]):n._e(),n._v(" "),1==e.GoodsSource||4==e.GoodsSource||7==e.GoodsSource?t("p",{staticClass:"grey"},[t("span",{staticClass:"l"},[n._v(n._s(e.DrName)+" "+n._s(e.PositionName))])]):n._e(),n._v(" "),5==e.GoodsSource?t("p",{staticClass:"grey"},[t("span",{staticClass:"l"},[n._v("知名三甲医院专家")]),n._v(" "),t("span",{staticClass:"r"},[n._v("x"+n._s(e.GoodsNum))])]):n._e(),n._v(" "),8==e.GoodsSource?t("p",{staticClass:"grey"},[n._v(n._s(e.GoodsSpecName))]):n._e(),n._v(" "),8==e.GoodsSource?t("p",{staticClass:"grey"},[n._v(n._s(e.GoodsClassName.split("#")[0])+" "+n._s(e.GoodsClassName.split("#")[1]))]):n._e(),n._v(" "),8==e.GoodsSource?t("p",{staticClass:"grey"},[t("span",{staticClass:"l"},[n._v(n._s(e.HospitalName)+" "+n._s(e.DepartmentName))]),n._v(" "),t("span",{staticClass:"r"},[n._v("x"+n._s(e.GoodsNum))])]):n._e(),n._v(" "),6==e.GoodsSource?t("p",{staticClass:"grey"},[t("span",{staticClass:"l"},[n._v(n._s(e.SpecialistTitle))]),n._v(" "),t("span",{staticClass:"r"},[n._v(n._s(e.PriceAttention))])]):n._e()])})):n._e(),n._v(" "),n.info?t("div",{staticClass:"price-box"},[t("p",[t("span",{staticClass:"l"},[n._v("商品金额")]),n._v(" "),t("span",{staticClass:"r"},[n._v("￥"+n._s(n.info.AllPrice))])]),n._v(" "),n.info&&n.info.CouponAmount&&Number(n.info.CouponAmount)>0?t("p",[t("span",{staticClass:"l"},[n._v("优惠")]),n._v(" "),t("span",{staticClass:"r"},[n._v("-￥"+n._s(n.info.CouponAmount))])]):n._e(),n._v(" "),n.info&&n.info.OtherAmount&&Number(n.info.OtherAmount)>0?t("p",[t("span",{staticClass:"l"},[n._v("附加费")]),n._v(" "),t("span",{staticClass:"r"},[n._v("+￥"+n._s(n.info.OtherAmount))])]):n._e(),n._v(" "),n.info&&n.info.AllPrice?t("div",{staticClass:"total"},[n._v("合计："),t("span",{staticClass:"fh"},[n._v("￥")]),n.totalPrice?t("span",{staticClass:"p"},[n._v(n._s(n.totalPrice))]):n._e()]):n._e()]):n._e(),n._v(" "),n.info&&n.info.CanSetUsed&&!n.$route.query.fromIMForAPP?t("div",{staticClass:"save-recom"},[t("span",{staticClass:"l"},[n._v("保存常用推荐")]),n._v(" "),t("span",{staticClass:"r",on:{click:function(e){n.changeRecom()}}},[t("span",{staticClass:"switch",class:{"switch-no":!n.isRecommend}},[t("i")])])]):n._e(),n._v(" "),n.$route.query.fromIMForAPP?n._e():t("div",{staticClass:"save-btn",class:{"btn-grey":!n.showBtn},on:{click:function(e){n.showPopup()}}},[n._v("发送推荐")])])},s=[],l={render:c,staticRenderFns:s},m=l,b=t("VU/8"),p=i,g=b(d,m,!1,p,"data-v-9862e924",null);e.default=g.exports},e99e:function(n,e,t){var i=t("kxFB");e=n.exports=t("FZ+f")(!1),e.push([n.i,"\n.content[data-v-9862e924] {\n  padding-top: 1.2rem;\n}\n.content .card[data-v-9862e924] {\n    padding: 0 0.4rem;\n    background: #fff;\n    margin-top: 0.13333rem;\n}\n.content .card .t[data-v-9862e924] {\n      height: 1.2rem;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      border-bottom: 1px solid #F1F1F1;\n      -webkit-box-pack: justify;\n      -webkit-justify-content: space-between;\n              justify-content: space-between;\n}\n.content .card .t > .l[data-v-9862e924] {\n        font-size: 15px;\n        color: #333333;\n}\n.content .card .t > .r[data-v-9862e924] {\n        font-size: 15px;\n        color: #FF4D00;\n}\n.content .card .item[data-v-9862e924] {\n      padding-top: 0.53333rem;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: start;\n      -webkit-align-items: flex-start;\n              align-items: flex-start;\n}\n.content .card .item > .l[data-v-9862e924] {\n        width: 1.92rem;\n}\n.content .card .item > .l > img[data-v-9862e924] {\n          display: block;\n          min-width: 1.70667rem;\n          max-width: 1.70667rem;\n          min-height: 1.70667rem;\n          max-height: 1.70667rem;\n          object-fit: contain;\n          border-radius: 0.13333rem;\n          border: none;\n          background: none;\n}\n.content .card .item > .r[data-v-9862e924] {\n        -webkit-box-flex: 1;\n        -webkit-flex: 1;\n                flex: 1;\n}\n.content .card .item > .r .n[data-v-9862e924] {\n          display: -webkit-box;\n          display: -webkit-flex;\n          display: flex;\n          -webkit-box-align: center;\n          -webkit-align-items: center;\n                  align-items: center;\n}\n.content .card .item > .r .n .l[data-v-9862e924] {\n            color: #333333;\n            font-size: 14px;\n            -webkit-box-flex: 1;\n            -webkit-flex: 1;\n                    flex: 1;\n            font-weight: 600;\n}\n.content .card .item > .r .n .r[data-v-9862e924] {\n            font-size: 16px;\n            color: #333333;\n            text-align: right;\n            width: 2.13333rem;\n}\n.content .card .item > .r .c[data-v-9862e924] {\n          padding-top: 0.21333rem;\n}\n.content .card .item > .r .c .dr[data-v-9862e924],\n          .content .card .item > .r .c .address[data-v-9862e924] {\n            display: -webkit-box;\n            display: -webkit-flex;\n            display: flex;\n            -webkit-box-align: center;\n            -webkit-align-items: center;\n                    align-items: center;\n}\n.content .card .item > .r .c .dr > i[data-v-9862e924],\n            .content .card .item > .r .c .address > i[data-v-9862e924] {\n              width: 0.34667rem;\n              height: 0.34667rem;\n              margin-right: 0.10667rem;\n              position: relative;\n              top: 0.02667rem;\n}\n.content .card .item > .r .c .dr .msg[data-v-9862e924],\n            .content .card .item > .r .c .address .msg[data-v-9862e924] {\n              -webkit-box-flex: 1;\n              -webkit-flex: 1;\n                      flex: 1;\n              font-size: 12px;\n              color: #666;\n}\n.content .card .item > .r .c .dr .r[data-v-9862e924],\n            .content .card .item > .r .c .address .r[data-v-9862e924] {\n              font-size: 12px;\n              color: #999999;\n}\n.content .card .item > .r .c .address[data-v-9862e924] {\n            padding-top: 0.08rem;\n}\n.content .card .item > .r .c .address .msg[data-v-9862e924] {\n              color: #999;\n}\n.content .card .total[data-v-9862e924] {\n      padding: 0.26667rem 0;\n      color: #333333;\n      font-size: 12px;\n      text-align: right;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      -webkit-box-pack: end;\n      -webkit-justify-content: flex-end;\n              justify-content: flex-end;\n      border-bottom: 1px solid #F1F1F1;\n}\n.content .card .total .fh[data-v-9862e924] {\n        font-size: 13px;\n        font-family: PingFangSC-Regular;\n}\n.content .card .total .num[data-v-9862e924] {\n        font-size: 18px;\n        font-family: PingFangSC-Regular;\n}\n.content .card .btn-box[data-v-9862e924] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      height: 1.2rem;\n}\n.content .card .btn-box .time[data-v-9862e924] {\n        -webkit-box-flex: 1;\n        -webkit-flex: 1;\n                flex: 1;\n}\n.content .card .btn-box .btn-b > span[data-v-9862e924] {\n        width: 1.68rem;\n        padding: 0.13333rem 0.26667rem;\n        border-radius: 0.34667rem;\n        color: #333333;\n        font-size: 0.32rem;\n}\n.content .card .btn-box .btn-b .btn[data-v-9862e924] {\n        border: 1px solid #BFBFBF;\n        color: #333333;\n}\n.content .card .btn-box .btn-b .btn2[data-v-9862e924] {\n        border: 1px solid #FF4D00;\n        color: #FF4D00;\n}\n.icon1[data-v-9862e924] {\n  background: url("+i(t("JWS5"))+") no-repeat center;\n  background-size: contain;\n}\n.icon2[data-v-9862e924] {\n  background: url("+i(t("KkBx"))+") no-repeat center;\n  background-size: contain;\n}\n#my-recom-detail[data-v-9862e924] {\n  padding-bottom: 3.73333rem;\n}\n#my-recom-detail .footer[data-v-9862e924] {\n    position: fixed;\n    width: 100%;\n    bottom: 0;\n    background: #F3F3F3;\n}\n#my-recom-detail .footer .foot[data-v-9862e924] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      padding: 0.4rem;\n      padding-bottom: 0.66667rem;\n      -webkit-box-pack: justify;\n      -webkit-justify-content: space-between;\n              justify-content: space-between;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n}\n#my-recom-detail .footer .foot > div[data-v-9862e924] {\n        width: 48%;\n        display: -webkit-box;\n        display: -webkit-flex;\n        display: flex;\n        -webkit-box-align: center;\n        -webkit-align-items: center;\n                align-items: center;\n        -webkit-box-pack: center;\n        -webkit-justify-content: center;\n                justify-content: center;\n        text-align: center;\n        height: 1.33333rem;\n        border-radius: 0.32rem;\n        font-size: 16px;\n        color: #fff;\n}\n#my-recom-detail .footer .foot .left[data-v-9862e924] {\n        background: #FFA417;\n        width: 80%;\n        margin: 0 auto;\n}\n#my-recom-detail .footer .foot .right[data-v-9862e924] {\n        background: #3069CF;\n}\n#my-recom-detail .order[data-v-9862e924] {\n    font-size: 14px;\n}\n#my-recom-detail .order .patient[data-v-9862e924] {\n      height: 1.28rem;\n      padding: 0 0.4rem;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      background: #fff;\n      border-top: 1px solid #f3f3f3;\n}\n#my-recom-detail .order .patient .n[data-v-9862e924] {\n        -webkit-box-flex: 1;\n        -webkit-flex: 1;\n                flex: 1;\n        display: -webkit-box;\n        display: -webkit-flex;\n        display: flex;\n        -webkit-box-align: center;\n        -webkit-align-items: center;\n                align-items: center;\n        height: 100%;\n}\n#my-recom-detail .order .patient .n > img[data-v-9862e924] {\n          min-width: 0.96rem;\n          min-height: 0.96rem;\n          max-width: 0.96rem;\n          max-height: 0.96rem;\n          border-radius: 50%;\n          margin-right: 0.26667rem;\n          display: block;\n}\n#my-recom-detail .order .patient .n .n-c[data-v-9862e924] {\n          font-size: 13px;\n          color: #333;\n          margin-right: 0.26667rem;\n}\n#my-recom-detail .order .patient .n .n-r[data-v-9862e924] {\n          font-size: 12px;\n          color: #999;\n}\n#my-recom-detail .order .patient .r[data-v-9862e924] {\n        width: 0.66667rem;\n        text-align: right;\n}\n#my-recom-detail .order .patient .r .enter[data-v-9862e924] {\n          display: inline-block;\n          margin-left: 0.13333rem;\n          width: 0.4rem;\n          height: 0.4rem;\n          position: relative;\n          top: 0.08rem;\n          background: url("+i(t("WjqE"))+") no-repeat center;\n          background-size: contain;\n}\n#my-recom-detail .order .content[data-v-9862e924] {\n      background: #fff;\n      padding: 0 0.4rem;\n      margin-top: 0.26667rem;\n      padding-bottom: 0.53333rem;\n}\n#my-recom-detail .order .content .card .item > .r .n .r[data-v-9862e924] {\n      color: #333333;\n      text-align: right;\n      font-weight: 600;\n      font-size: 14px;\n}\n#my-recom-detail .order .content[data-v-9862e924] {\n      padding: 0 0.4rem;\n      background: #fff;\n      margin-top: 0.26667rem;\n}\n#my-recom-detail .order .content .tit[data-v-9862e924] {\n        border-bottom: 1px solid #ececec;\n        height: 1.2rem;\n        display: -webkit-box;\n        display: -webkit-flex;\n        display: flex;\n        -webkit-box-align: center;\n        -webkit-align-items: center;\n                align-items: center;\n        color: #333;\n        font-size: 0.4rem;\n}\n#my-recom-detail .order .content .tit > i[data-v-9862e924] {\n          margin-right: 0.13333rem;\n          width: 0.42667rem;\n          height: 0.42667rem;\n          background: url("+i(t("wwMt"))+") no-repeat center;\n          background-size: contain;\n}\n#my-recom-detail .order .content .card[data-v-9862e924] {\n        padding: 0;\n        margin: 0;\n        padding-bottom: 0.53333rem;\n}\n#my-recom-detail .order .content .card .item .r .n[data-v-9862e924] {\n          -webkit-box-align: start;\n          -webkit-align-items: flex-start;\n                  align-items: flex-start;\n}\n#my-recom-detail .order .content .card .item .r .n .l > span > span[data-v-9862e924] {\n            color: #FF0000;\n}\n#my-recom-detail .order .content .card .item .btn[data-v-9862e924] {\n          width: 2.02667rem;\n          text-align: right;\n}\n#my-recom-detail .order .content .card .item .btn .blue-btn[data-v-9862e924] {\n            color: #FFFFFF;\n            font-size: 12px;\n            padding: 0rem 20;\n            height: 0.69333rem;\n            line-height: 0.74667rem;\n            border-radius: 0.34667rem;\n            background: -webkit-gradient(linear, 0 100%, 0 0, from(#148FFE), to(#2C66D3));\n            background: -webkit-linear-gradient(90deg, #148FFE, #2C66D3);\n            background: -webkit-linear-gradient(left, #148FFE, #2C66D3);\n            background: linear-gradient(90deg, #148FFE, #2C66D3);\n            display: block;\n            text-align: center;\n            width: 1.81333rem;\n            margin-left: 0.21333rem;\n}\n#my-recom-detail .order .content .card .item .btn .grey-btn[data-v-9862e924] {\n            color: #FFFFFF;\n            font-size: 12px;\n            padding: 0rem 20;\n            height: 0.69333rem;\n            line-height: 0.74667rem;\n            border-radius: 0.34667rem;\n            display: block;\n            text-align: center;\n            background: #CCCCCC;\n            width: 1.81333rem;\n            margin-left: 0.21333rem;\n}\n#my-recom-detail .order .pay-box[data-v-9862e924] {\n      padding: 0 0.4rem;\n      background: #fff;\n}\n#my-recom-detail .order .pay-box .total[data-v-9862e924] {\n        border-top: 1px solid #F1F1F1;\n        height: 1.2rem;\n        display: -webkit-box;\n        display: -webkit-flex;\n        display: flex;\n        -webkit-box-align: center;\n        -webkit-align-items: center;\n                align-items: center;\n        -webkit-box-pack: end;\n        -webkit-justify-content: flex-end;\n                justify-content: flex-end;\n        color: #333333;\n        font-size: 15px;\n        text-align: right;\n}\n#my-recom-detail .order .pay-box .total > .fh[data-v-9862e924] {\n          color: #FF4D00;\n          font-size: 13px;\n}\n#my-recom-detail .order .pay-box .total > .p[data-v-9862e924] {\n          color: #FF4D00;\n          font-size: 18px;\n          font-weight: 600;\n}\n#my-recom-detail .recom-msg[data-v-9862e924] {\n    padding: 0.16rem 0.4rem 0.42667rem 0.4rem;\n    margin: 0;\n    margin-top: 0.26667rem;\n    background: #fff;\n}\n#my-recom-detail .recom-msg > p[data-v-9862e924] {\n      color: #333;\n      font-size: 13px;\n      padding-top: 0.26667rem;\n}\n#re-detail .title .edit[data-v-9862e924] {\n  color: #2C66D3;\n  font-size: 15px;\n  padding: 0.4rem;\n}\n#re-detail .qr-box[data-v-9862e924] {\n  background: #fff;\n  margin-top: 0.26667rem;\n  padding: 0.53333rem 0.4rem;\n}\n#re-detail .qr-box > h4[data-v-9862e924] {\n    font-size: 16px;\n    text-align: center;\n    font-weight: 600;\n    color: #333;\n}\n#re-detail .qr-box .tit[data-v-9862e924] {\n    color: #333;\n    font-size: 12px;\n    margin-top: 0.26667rem;\n    text-align: center;\n}\n#re-detail .qr-box .qr-code > img[data-v-9862e924], #re-detail .qr-box .qr-code > i[data-v-9862e924] {\n    display: block;\n    margin: 0 auto;\n    width: 2.66667rem;\n    height: 2.66667rem;\n    margin-top: 0.8rem;\n}\n#re-detail .qr-box .qr-code > i[data-v-9862e924] {\n    background: #ccc;\n}\n#re-detail .content[data-v-9862e924] {\n  padding: 0.26667rem 0.4rem;\n  padding-top: 0;\n  background: #fff;\n  font-size: 14px;\n  color: #333;\n}\n#re-detail .content .item[data-v-9862e924]:first-child {\n    border-top: 1px solid #F1F1F1;\n}\n#re-detail .content .item[data-v-9862e924] {\n    padding-top: 0.26667rem;\n}\n#re-detail .content .item .n[data-v-9862e924] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: start;\n      -webkit-align-items: flex-start;\n              align-items: flex-start;\n}\n#re-detail .content .item .n .l[data-v-9862e924] {\n        -webkit-box-flex: 1;\n        -webkit-flex: 1;\n                flex: 1;\n        font-weight: 600;\n}\n#re-detail .content .item .n .r[data-v-9862e924] {\n        width: 2.13333rem;\n        text-align: right;\n}\n#re-detail .content .item .grey[data-v-9862e924] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      color: #999;\n      font-size: 12px;\n      margin-top: 0.10667rem;\n}\n#re-detail .content .item .grey .l[data-v-9862e924] {\n        -webkit-box-flex: 1;\n        -webkit-flex: 1;\n                flex: 1;\n}\n#re-detail .content .item .grey .r[data-v-9862e924] {\n        width: 3.46667rem;\n        text-align: right;\n}\n#re-detail .price-box[data-v-9862e924] {\n  margin-top: 0.26667rem;\n  padding: 0.53333rem 0.4rem;\n  padding-bottom: 0;\n  background: #fff;\n  font-size: 13px;\n  color: #333;\n}\n#re-detail .price-box > p[data-v-9862e924] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    padding-bottom: 0.37333rem;\n}\n#re-detail .price-box > p > span[data-v-9862e924] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n}\n#re-detail .price-box > p .r[data-v-9862e924] {\n      color: #FF4D00;\n      text-align: right;\n}\n#re-detail .price-box .total[data-v-9862e924] {\n    border-top: 1px solid #F3F3F3;\n    font-size: 14px;\n    color: #333;\n    text-align: right;\n    padding: 0.26667rem 0;\n}\n#re-detail .price-box .total .fh[data-v-9862e924] {\n      color: #FF4D00;\n      font-size: 13px;\n}\n#re-detail .price-box .total .p[data-v-9862e924] {\n      color: #FF4D00;\n      font-size: 18px;\n}\n#re-detail .save-recom[data-v-9862e924] {\n  margin-top: 0.26667rem;\n  padding: 0 0.4rem;\n  background: #fff;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  font-size: 0.4rem;\n  color: #333;\n  height: 1.28rem;\n}\n#re-detail .save-recom .l[data-v-9862e924] {\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n}\n#re-detail .save-recom .switch[data-v-9862e924] {\n    display: block;\n    position: relative;\n    width: 1.46667rem;\n    height: 0.8rem;\n    border-radius: 0.4rem;\n    background: #2C66D3;\n}\n#re-detail .save-recom .switch > i[data-v-9862e924] {\n      width: 0.74667rem;\n      height: 0.74667rem;\n      border-radius: 0.37333rem;\n      position: absolute;\n      right: 0.02667rem;\n      top: 0.02667rem;\n      left: auto;\n      background: #fff;\n      margin-right: 0;\n}\n#re-detail .save-recom .switch-no[data-v-9862e924] {\n    display: block;\n    position: relative;\n    width: 1.46667rem;\n    height: 0.8rem;\n    border-radius: 0.4rem;\n    background: #ccc;\n}\n#re-detail .save-recom .switch-no > i[data-v-9862e924] {\n      width: 0.74667rem;\n      height: 0.74667rem;\n      border-radius: 0.37333rem;\n      position: absolute;\n      left: 0.02667rem;\n      top: 0.02667rem;\n      right: auto;\n      background: #fff;\n      margin-right: 0;\n}\n#re-detail .save-btn[data-v-9862e924] {\n  margin: 0.26667rem 10%;\n  margin-bottom: 0.8rem;\n  text-align: center;\n  background: #2C66D3;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  color: #FFFFFF;\n  height: 1.06667rem;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  border-radius: 0.53333rem;\n  font-size: 16px;\n  background: -webkit-gradient(linear, 0 100%, 0 0, from(#148FFE), to(#2C66D3));\n  background: -webkit-linear-gradient(90deg, #148FFE, #2C66D3);\n  background: -webkit-linear-gradient(left, #148FFE, #2C66D3);\n  background: linear-gradient(90deg, #148FFE, #2C66D3);\n}\n#re-detail .btn-grey[data-v-9862e924] {\n  background: #ccc;\n}\n#re-detail-edit[data-v-9862e924] {\n  margin-bottom: 2.66667rem;\n}\n#re-detail-edit .content[data-v-9862e924] {\n    margin-top: 0.26667rem;\n    padding: 0.26667rem 0.4rem;\n    padding-top: 0;\n    background: #fff;\n    font-size: 14px;\n}\n#re-detail-edit .content .item[data-v-9862e924] {\n      padding-top: 0.26667rem;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: start;\n      -webkit-align-items: flex-start;\n              align-items: flex-start;\n}\n#re-detail-edit .content .item > .l[data-v-9862e924] {\n        width: 2.72rem;\n}\n#re-detail-edit .content .item > .l > img[data-v-9862e924] {\n          display: block;\n          min-width: 2.45333rem;\n          max-width: 2.45333rem;\n          min-height: 2.45333rem;\n          max-height: 2.45333rem;\n          object-fit: contain;\n          border-radius: 0.26667rem;\n          border: none;\n          background: none;\n}\n#re-detail-edit .content .item .r[data-v-9862e924] {\n        -webkit-box-flex: 1;\n        -webkit-flex: 1;\n                flex: 1;\n}\n#re-detail-edit .content .item .r .n[data-v-9862e924] {\n          color: #333;\n          font-size: 16px;\n          font-weight: 600;\n}\n#re-detail-edit .content .item .r .grey[data-v-9862e924] {\n          display: -webkit-box;\n          display: -webkit-flex;\n          display: flex;\n          -webkit-box-align: center;\n          -webkit-align-items: center;\n                  align-items: center;\n          color: #666;\n          font-size: 12px;\n          margin-top: 0.13333rem;\n}\n#re-detail-edit .content .item .r .grey > i[data-v-9862e924] {\n            width: 0.34667rem;\n            height: 0.34667rem;\n            margin-right: 0.10667rem;\n}\n#re-detail-edit .content .item .r .grey .icon1[data-v-9862e924] {\n            background: url("+i(t("JWS5"))+") no-repeat center;\n            background-size: contain;\n}\n#re-detail-edit .content .item .r .grey .icon2[data-v-9862e924] {\n            background: url("+i(t("KkBx"))+") no-repeat center;\n            background-size: contain;\n}\n#re-detail-edit .content .item .r .opt[data-v-9862e924] {\n          display: -webkit-box;\n          display: -webkit-flex;\n          display: flex;\n          -webkit-box-align: center;\n          -webkit-align-items: center;\n                  align-items: center;\n          padding-top: 0.45333rem;\n}\n#re-detail-edit .content .item .r .opt .p[data-v-9862e924] {\n            color: #FF4D00;\n            font-size: 14px;\n            width: 2.53333rem;\n}\n#re-detail-edit .content .item .r .opt .num[data-v-9862e924] {\n            display: -webkit-box;\n            display: -webkit-flex;\n            display: flex;\n            -webkit-box-align: center;\n            -webkit-align-items: center;\n                    align-items: center;\n            border: 1px solid #CCCCCC;\n            margin-right: 0.53333rem;\n}\n#re-detail-edit .content .item .r .opt .num > input[data-v-9862e924] {\n              border: none;\n              background: none;\n              text-align: center;\n              width: 1.17333rem;\n              font-size: 14px;\n              color: #333;\n}\n#re-detail-edit .content .item .r .opt .num > span[data-v-9862e924] {\n              width: 0.58667rem;\n              height: 0.58667rem;\n              text-align: center;\n              font-size: 18px;\n              line-height: 0.58667rem;\n}\n#re-detail-edit .content .item .r .opt .num > span.sub[data-v-9862e924] {\n              background: #ccc;\n              color: #fff;\n}\n#re-detail-edit .content .item .r .opt .num > span.add[data-v-9862e924] {\n              border-left: 1px solid #ccc;\n              color: #ccc;\n}\n#re-detail-edit .content .item .r .opt .delete[data-v-9862e924] {\n            padding-left: 0.4rem;\n            text-align: right;\n}\n#re-detail-edit .content .item .r .opt .delete > i[data-v-9862e924] {\n              background: url("+i(t("uZQh"))+") no-repeat center;\n              background-size: contain;\n              width: 0.42667rem;\n              height: 0.42667rem;\n              display: block;\n}\n#re-detail-edit .add-btn[data-v-9862e924] {\n    background: #fff;\n    padding: 0.53333rem 12.7%;\n    padding-top: 0;\n}\n#re-detail-edit .add-btn > div[data-v-9862e924] {\n      color: #2C66D3;\n      border: 1px dashed #2C66D3;\n      font-size: 14px;\n      text-align: center;\n      border-radius: 0.53333rem;\n      height: 1.06667rem;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      -webkit-box-pack: center;\n      -webkit-justify-content: center;\n              justify-content: center;\n}\n#re-detail-edit .add-btn > div > i[data-v-9862e924] {\n        background: url("+i(t("7tSR"))+") no-repeat center;\n        background-size: contain;\n        width: 0.4rem;\n        height: 0.4rem;\n        margin-right: 0.21333rem;\n}\n#re-detail-edit .footer[data-v-9862e924] {\n    position: fixed;\n    left: 0;\n    right: 0;\n    bottom: 0;\n}\n#re-detail-edit .footer .save-btn[data-v-9862e924] {\n      margin: 0.26667rem 10%;\n      text-align: center;\n      background: #2C66D3;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      color: #FFFFFF;\n      height: 1.06667rem;\n      -webkit-box-pack: center;\n      -webkit-justify-content: center;\n              justify-content: center;\n      border-radius: 0.53333rem;\n      font-size: 16px;\n      background: -webkit-gradient(linear, 0 100%, 0 0, from(#148FFE), to(#2C66D3));\n      background: -webkit-linear-gradient(90deg, #148FFE, #2C66D3);\n      background: -webkit-linear-gradient(left, #148FFE, #2C66D3);\n      background: linear-gradient(90deg, #148FFE, #2C66D3);\n}\n#my-recom .noData[data-v-9862e924] {\n  background: #fff;\n  margin-top: 0.13333rem;\n  font-size: 14px;\n}\n#my-recom .recom-box[data-v-9862e924] {\n  position: fixed;\n  top: 2.4rem;\n  left: 0;\n  right: 0;\n  height: 1.38667rem;\n  z-index: 10;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  background: #f3f3f3;\n}\n#my-recom .recom-box > div[data-v-9862e924] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    width: 80%;\n    border-radius: 2.66667rem;\n    border: 1px solid #2C66D3;\n    overflow: hidden;\n}\n#my-recom .recom-box > div > .on[data-v-9862e924] {\n      background: #2C66D3;\n      color: #FFFFFF;\n}\n#my-recom .recom-box > div p[data-v-9862e924] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      font-size: 16px;\n      text-align: center;\n      padding: 0.13333rem 0;\n      background: #fff;\n      color: #2C66D3;\n}\n#my-recom .searchBox[data-v-9862e924] {\n  height: 1.17333rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: justify;\n  -webkit-justify-content: space-between;\n          justify-content: space-between;\n  padding: 0 0.4rem;\n  position: fixed;\n  top: 1.2rem;\n  left: 0;\n  right: 0;\n  background: #fff;\n  border-top: 1px solid #f3f3f3;\n  z-index: 10;\n}\n#my-recom .searchBox > span.search[data-v-9862e924] {\n    height: 0.74667rem;\n    border: 1px solid #999999;\n    border-radius: 0.37333rem;\n    background: #fff;\n    width: 84%;\n    padding-left: 10%;\n    display: block;\n    position: relative;\n}\n#my-recom .searchBox > span.search > i[data-v-9862e924] {\n      position: absolute;\n      left: 0.34667rem;\n      top: 0.10667rem;\n      background: url("+i(t("wZQx"))+") no-repeat center;\n      background-size: contain;\n      width: 0.4rem;\n      height: 0.4rem;\n}\n#my-recom .searchBox > span.search > input[data-v-9862e924] {\n      width: 95%;\n      font-size: 14px;\n      color: #333;\n}\n#my-recom .searchBox .btn[data-v-9862e924] {\n    padding: 0.13333rem 0.4rem;\n    padding-right: 0.13333rem;\n    color: #2C66D3;\n    font-size: 13px;\n}\n#my-recom .sort[data-v-9862e924] {\n  height: 1.06667rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  padding: 0 0.4rem;\n  position: fixed;\n  top: 3.78667rem;\n  left: 0;\n  right: 0;\n  z-index: 10;\n  background: #fff;\n}\n#my-recom .sort > p[data-v-9862e924] {\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    text-align: center;\n    color: #333333;\n    font-size: 14px;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n}\n#my-recom .sort > p > i[data-v-9862e924] {\n      background: url("+i(t("BMhy"))+") no-repeat center;\n      background-size: contain;\n      width: 0.18667rem;\n      height: 0.13333rem;\n      margin-left: 0.13333rem;\n}\n#my-recom .sort > p > i.up[data-v-9862e924] {\n      background: url("+i(t("d0Xe"))+") no-repeat center;\n      background-size: contain;\n}\n#my-recom .sort .up[data-v-9862e924] {\n    color: #2C66D3;\n}\n#my-recom .sortList[data-v-9862e924] {\n  padding: 0 0.4rem;\n  position: fixed;\n  top: 4.82667rem;\n  left: 0;\n  right: 0;\n  z-index: 10;\n  background: #fff;\n  border-top: 1px solid #F3F3F3;\n}\n#my-recom .sortList > p[data-v-9862e924] {\n    height: 1.06667rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    font-size: 14px;\n    color: #333;\n}\n#my-recom .sortList .on[data-v-9862e924] {\n    color: #2C66D3;\n}\n#my-recom .reload[data-v-9862e924] {\n  text-align: center;\n  color: #999;\n  padding: 0.13333rem 0;\n  font-size: 14px;\n}\n#my-recom .content[data-v-9862e924] {\n  position: fixed;\n  top: 3.62667rem;\n  bottom: 0;\n  left: 0;\n  right: 0;\n  overflow: scroll;\n  -webkit-overflow-scrolling: touch;\n  -webkit-transform: translateZ(0px);\n  z-index: 1;\n  padding-bottom: 1.06667rem;\n}\n#my-recom .content .card[data-v-9862e924] {\n    margin-top: 0.26667rem;\n    padding: 0;\n    border-radius: 0.26667rem;\n    border: 1px solid #fff;\n}\n#my-recom .content .card .info[data-v-9862e924], #my-recom .content .card .doctor[data-v-9862e924] {\n      padding: 0.4rem 0.26667rem;\n      font-size: 14px;\n}\n#my-recom .content .card .info > p[data-v-9862e924], #my-recom .content .card .doctor > p[data-v-9862e924] {\n        padding-top: 0.10667rem;\n        color: #999999;\n        font-size: 14px;\n}\n#my-recom .content .card .info .col-3[data-v-9862e924], #my-recom .content .card .doctor .col-3[data-v-9862e924] {\n        color: #333;\n}\n#my-recom .content .card .info > .flex[data-v-9862e924], #my-recom .content .card .doctor > .flex[data-v-9862e924] {\n        display: -webkit-box;\n        display: -webkit-flex;\n        display: flex;\n        -webkit-box-align: center;\n        -webkit-align-items: center;\n                align-items: center;\n        -webkit-box-pack: justify;\n        -webkit-justify-content: space-between;\n                justify-content: space-between;\n}\n#my-recom .content .card .info > .flex .r[data-v-9862e924], #my-recom .content .card .doctor > .flex .r[data-v-9862e924] {\n          color: #FFFFFF;\n          font-size: 13px;\n          border: 1px solid #FF4D00;\n          color: #ff4d00;\n          text-align: center;\n          width: 1.86667rem;\n          margin-left: 0.21333rem;\n          padding: 0.08rem 0;\n          border-radius: 0.34667rem;\n}\n#my-recom .content .card .info[data-v-9862e924] {\n      border-bottom: 1px solid #F3F3F3;\n}\n#my-recom .content .card .info .t-box[data-v-9862e924] {\n        display: -webkit-box;\n        display: -webkit-flex;\n        display: flex;\n        -webkit-box-align: center;\n        -webkit-align-items: center;\n                align-items: center;\n        padding-bottom: 0.16rem;\n}\n#my-recom .content .card .info .t-box .t-n[data-v-9862e924] {\n          -webkit-box-flex: 1;\n          -webkit-flex: 1;\n                  flex: 1;\n          color: #333333;\n          font-size: 15px;\n          font-weight: 600;\n}\n#my-recom .content .card .info .t-box .payStatus[data-v-9862e924] {\n          width: 1.86667rem;\n          color: #FF4D00;\n          font-size: 14px;\n          text-align: right;\n}\n#my-recom .mask[data-v-9862e924] {\n  position: fixed;\n  top: 0;\n  right: 0;\n  left: 0;\n  bottom: 0;\n  background: rgba(0, 0, 0, 0.5);\n  z-index: 2;\n}\n#my-recom .noData[data-v-9862e924] {\n  padding-top: 5.33333rem;\n}\n",""])}});