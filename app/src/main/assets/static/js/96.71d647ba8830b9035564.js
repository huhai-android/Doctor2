webpackJsonp([96],{BXMi:function(t,e,n){var i=n("Jg+m");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);n("rjj0")("8b228362",i,!0,{})},"Jg+m":function(t,e,n){e=t.exports=n("FZ+f")(!1),e.push([t.i,"\n.heightW[data-v-751fbee8] {\n  height: 94vh;\n  background-color: #fff;\n  overflow: scroll;\n}\n.heightW .preTit[data-v-751fbee8] {\n    background: #fff;\n    padding: 0 0.4rem;\n    height: 1.33333rem;\n    line-height: 1.33333rem;\n    text-align: center;\n    font-size: 16px;\n    border-top: 1px #eee solid;\n}\n.heightW .preTit .tip[data-v-751fbee8] {\n      font-size: 13px;\n      max-width: 1.6rem;\n      text-align: center;\n      border: 1px solid #000;\n      padding: 0.06667rem 0.06667rem;\n}\n.heightW .preTit i[data-v-751fbee8] {\n      display: block;\n      width: 1.6rem;\n}\n.heightW .info[data-v-751fbee8] {\n    padding: 0.4rem 0.4rem 0;\n    background: #fff;\n}\n.heightW .info .card[data-v-751fbee8] {\n      font-size: 12px;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      -webkit-box-pack: justify;\n      -webkit-justify-content: space-between;\n              justify-content: space-between;\n      padding: 0.06667rem 0;\n      margin-top: 0.13333rem;\n}\n.heightW .info .card[data-v-751fbee8]:first-child {\n        border-bottom: 1px solid #000;\n        padding-bottom: 0.13333rem;\n}\n.heightW .info .card[data-v-751fbee8]:last-child {\n        border-bottom: 1px solid #000;\n        padding-bottom: 0.13333rem;\n}\n.heightW .info .card p[data-v-751fbee8] {\n        min-width: 33%;\n        margin: 0 0.06667rem;\n}\n.heightW .info .card p span[data-v-751fbee8]:first-child {\n          white-space: nowrap;\n}\n.heightW .info .card1 p[data-v-751fbee8] {\n      max-width: 100%;\n}\n.heightW .info .card2[data-v-751fbee8] {\n      -webkit-box-pack: start;\n      -webkit-justify-content: flex-start;\n              justify-content: flex-start;\n}\n.heightW .med[data-v-751fbee8] {\n    background: #fff;\n    padding: 0.4rem;\n    min-height: 8rem;\n    font-size: 16px;\n}\n.heightW .med .tit[data-v-751fbee8] {\n      font-size: 23px;\n      font-weight: bold;\n}\n.heightW .med .list[data-v-751fbee8] {\n      padding: 0 0 0.26667rem 0;\n      font-size: 15px;\n}\n.heightW .med .list table[data-v-751fbee8] {\n        width: 100%;\n}\n.heightW .med .list table tr[data-v-751fbee8] {\n          height: 0.8rem;\n          font-size: 12px;\n}\n.heightW .med .list table tr td[data-v-751fbee8] {\n            text-align: center;\n}\n.heightW .med .list table tr td[data-v-751fbee8]:nth-child(2) {\n            white-space: normal;\n            text-align: left;\n}\n.heightW .med .list table tr td[data-v-751fbee8]:nth-child(1) {\n            text-align: left;\n}\n.heightW .med .list table tr td[data-v-751fbee8]:nth-child(4) {\n            text-align: right;\n}\n.heightW .med .list table td[data-v-751fbee8] {\n          white-space: nowrap;\n          text-align: center;\n}\n.heightW .med .list table tr[data-v-751fbee8]:nth-child(1) {\n          font-size: 14px;\n}\n.heightW .med .list table tr:nth-child(1) td[data-v-751fbee8] {\n            text-align: center;\n}\n.heightW .med .list table tr:nth-child(1) td[data-v-751fbee8]:nth-child(1), .heightW .med .list table tr:nth-child(1) td[data-v-751fbee8]:nth-child(2) {\n            text-align: left;\n}\n.heightW .total[data-v-751fbee8] {\n    text-align: right;\n    font-size: 16px;\n    line-height: 1.33333rem;\n    border-top: 1px #000 solid;\n    border-bottom: 1px #000 solid;\n}\n.heightW .timestart[data-v-751fbee8] {\n    margin-top: 0.2rem;\n    font-size: 14px;\n}\n",""])},Ub1e:function(t,e,n){"use strict";function i(t){n("BXMi")}Object.defineProperty(e,"__esModule",{value:!0});var a=n("pKZN"),s=(a.a,{components:{back:a.a},data:function(){return{info:"",i:[],time:null}},created:function(){this.pull()},methods:{pull:function(){var t=this;this.$get("/InspectionAPI/Censorate/CensorateRecordAndDetail",{CensorateRecordId:this.$route.query.CensorateRecordId}).then(function(e){t.info=e,t.i=e.Details,t.time=e.PayOrderTime.split(" ")[0]})}}}),d=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"pdt"},[n("div",{staticClass:"title"},[n("back"),t._v(" "),n("span",{staticClass:"name"},[t._v("检查单")]),t._v(" "),n("a")],1),t._v(" "),n("div",{staticClass:"heightW"},[n("div",{staticClass:"black"},[n("p",{staticClass:"preTit"},[t._v("\n                    新起点互联网医院检查申请单\n                ")]),t._v(" "),n("div",{staticClass:"info"},[n("div",{staticClass:"card"},[n("p",[n("span",[t._v("费别：")]),t._v(" "),n("span",[t._v(t._s(t.info.CostType))])]),t._v(" "),n("p",[n("span",[t._v("检查单编号：")]),t._v(" "),n("span",[t._v(t._s(t.info.BillNo))])])]),t._v(" "),n("div",{staticClass:"card"},[n("p",[n("span",[t._v("姓名：")]),t._v(" "),n("span",[t._v(t._s(t.info.PatientName))])]),t._v(" "),n("p",[n("span",[t._v("性别：")]),t._v(" "),n("span",[t._v(t._s(t._f("toSex")(t.info.PatientSex)))])]),t._v(" "),n("p",[n("span",[t._v("年龄：")]),t._v(" "),n("span",[t._v(t._s(t.info.PatientAge))])])]),t._v(" "),n("div",{staticClass:"card"},[n("p",[n("span",[t._v("门诊病历号：")]),t._v(" "),n("span",[t._v(t._s(t.info.MedicalRecordNo))])]),t._v(" "),n("p",[n("span",[t._v("科别：")]),t._v(" "),n("span",[t._v(t._s(t.info.Department))])]),t._v(" "),n("p")]),t._v(" "),n("div",{staticClass:"card card2"},[n("p",[n("span",[t._v("主诊医生：")]),t._v(" "),n("span",[t._v(t._s(t.info.DrName))])]),t._v(" "),n("p",[n("span",[t._v("开具日期：")]),t._v(" "),n("span",[t._v(t._s(t.time))])])]),t._v(" "),n("div",{staticClass:"card"},[n("p",[n("span",[t._v("电话：")]),t._v(" "),n("span",[t._v(t._s(t.info.PatientMobile))])])]),t._v(" "),n("div",{staticClass:"card card1"},[n("p",[n("span",[t._v("临床情况：")]),t._v(" "),n("span",[t._v(t._s(t.info.ClinicDesc))])])]),t._v(" "),n("div",{staticClass:"card card1"},[n("p",[n("span",[t._v("临床拟诊：")]),t._v(" "),n("span",[t._v(t._s(t.info.ClinicImpression))])])])]),t._v(" "),n("div",{staticClass:"med"},[n("div",{staticClass:"list"},[n("table",[t._m(0),t._v(" "),t._l(t.i,function(e,i){return n("tr",{key:i},[n("td",[t._v(t._s(e.CensorateItemCode))]),t._v(" "),n("td",[t._v(t._s(e.CensorateItemName))]),t._v(" "),n("td",[t._v(t._s(e.ItemNum))]),t._v(" "),n("td",[t._v("￥"+t._s(e.ItemPrice))])])}),t._v(" "),n("tr",[n("td",[t._v(" ")]),t._v(" "),n("td",[t._v("附加费")]),t._v(" "),n("td",[t._v(" ")]),t._v(" "),n("td",[t._v("￥"+t._s(t.info.OtherAmount))])])],2)]),t._v(" "),n("p",{staticClass:"total"},[t._v("总计："+t._s(t.info.PayAmount))]),t._v(" "),n("p",{staticClass:"timestart"},[t._v(t._s(t.info.PayOrderTime))])])])])])},r=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("tr",[n("td",[t._v("项目代码")]),t._v(" "),n("td",[t._v("项目名称")]),t._v(" "),n("td",[t._v("数量")]),t._v(" "),n("td",[t._v(" ")])])}],v={render:d,staticRenderFns:r},o=v,l=n("VU/8"),h=i,f=l(s,o,!1,h,"data-v-751fbee8",null);e.default=f.exports}});