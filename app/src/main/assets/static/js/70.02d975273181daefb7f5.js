webpackJsonp([70],{"85Lm":function(n,e,t){"use strict";function i(n){t("uPwl")}Object.defineProperty(e,"__esModule",{value:!0});var a=t("pKZN"),r=t("aTeQ"),o=(a.a,r.a,{components:{back:a.a,XSwitch:r.a},data:function(){return{list:null,drMsg:this.$store.state.userInfo.Data.User,stringValue:!1,money:50,num:0}},created:function(){this.getList()},mounted:function(){},methods:{getList:function(){var n=this;this.$get("PlatFormAPI/SpecialistHosGroup/QuerySpecialistReferralSet",{DrId:this.drMsg.DoctorId}).then(function(e){0==e.IsSpecialReferral?n.stringValue=!1:(n.stringValue=!0,n.money=e.SpecialReferralAmount)})},moneyMsg:function(){Number(this.money)>99999&&this.$toast("接诊单价不能超过10万元")},goTo:function(){var n=this;if(Number(this.money)>99999)return this.$toast("接诊单价不能超过10万元");this.stringValue?this.num=1:this.num=0,this.$post("PlatFormAPI/SpecialistHosGroup/SaveSpecialistReferralSet",{DrId:this.drMsg.DoctorId,IsSpecialReferral:this.num,SpecialReferralAmount:this.money}).then(function(e){n.$toast("设置成功"),setTimeout(function(){n.$router.push("/setReferralList")},2e3)})}}}),s=function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("div",{staticClass:"pdt"},[t("div",{staticClass:"title"},[t("back"),n._v(" "),t("span",{staticClass:"name"},[n._v("专科团队转诊设置")]),n._v(" "),t("a")],1),n._v(" "),t("div",{staticClass:"items"},[t("div",{staticClass:"second"},[t("p",[n._v("\n\t\t\t\t\t接转诊开关\n\t\t\t\t\t"),t("x-switch",{attrs:{title:""},model:{value:n.stringValue,callback:function(e){n.stringValue=e},expression:"stringValue"}})],1),n._v(" "),t("p",[n._v("接诊单价\n\t\t\t\t\t"),t("span",[t("input",{directives:[{name:"model",rawName:"v-model",value:n.money,expression:"money"}],attrs:{type:"number"},domProps:{value:n.money},on:{keyup:function(e){if(!("button"in e)&&n._k(e.keyCode,"enter",13,e.key,"Enter"))return null;n.moneyMsg()},input:[function(e){e.target.composing||(n.money=e.target.value)},function(e){n.moneyMsg()}]}}),n._v("元")])])])]),n._v(" "),t("div",{staticClass:"save",on:{click:function(e){n.goTo()}}},[n._v("保存")])])},c=[],l={render:s,staticRenderFns:c},d=l,f=t("VU/8"),m=i,u=f(o,d,!1,m,"data-v-ca25fc24",null);e.default=u.exports},BCkX:function(n,e,t){var i=t("kxFB");e=n.exports=t("FZ+f")(!1),e.push([n.i,"\n.pdt[data-v-ca25fc24] {\n  padding-bottom: 0;\n}\n.title[data-v-ca25fc24] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  height: 1.2rem;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  background: #fff;\n}\n.title i[data-v-ca25fc24] {\n    display: block;\n    height: 0.46667rem;\n    width: 0.26667rem;\n    background: url("+i(t("P9/e"))+") no-repeat center;\n    background-size: cover;\n}\n.title input[data-v-ca25fc24] {\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    height: 70%;\n    margin-left: 0.4rem;\n    background: #f4f4f4;\n    border-radius: 20px;\n    padding: 0 1.06667rem;\n}\n.title a[data-v-ca25fc24] {\n    color: #306bce;\n}\n.title b[data-v-ca25fc24] {\n    display: block;\n    width: 0.53333rem;\n    height: 0.53333rem;\n    background: url("+i(t("wIlF"))+") no-repeat center;\n    position: absolute;\n    left: 1.46667rem;\n}\n.save[data-v-ca25fc24] {\n  display: block;\n  width: 80%;\n  text-align: center;\n  line-height: 1.06667rem;\n  background-color: #2e69cd;\n  border-radius: 0.4rem;\n  color: #fff;\n  font-style: normal;\n  margin: 1.06667rem auto;\n}\n.items[data-v-ca25fc24] {\n  background: #fff;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  margin-top: 0.4rem;\n}\n.items .first[data-v-ca25fc24] {\n    width: 40%;\n    height: 90vh;\n    overflow: scroll;\n    background: #e4e4e4;\n}\n.items .first p[data-v-ca25fc24] {\n      border-bottom: 1px solid #fff;\n}\n.items .first .on[data-v-ca25fc24] {\n      background: #fff;\n}\n.items .second[data-v-ca25fc24] {\n    width: 100%;\n}\n.items .second p[data-v-ca25fc24] {\n      border-bottom: 1px solid #f5f5f5;\n      margin: 0 0.4rem;\n      padding: 0;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-pack: justify;\n      -webkit-justify-content: space-between;\n              justify-content: space-between;\n      font-size: 0.37333rem;\n      color: #666;\n      height: 1.2rem;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n}\n.items .second p > b[data-v-ca25fc24] {\n        width: 2.66667rem;\n        font-weight: 500;\n        color: #999;\n        font-size: 0.37333rem;\n        text-align: right;\n}\n.items .second p > i[data-v-ca25fc24] {\n        width: 0.4rem;\n        height: 0.4rem;\n        background: url("+i(t("kD6O"))+") no-repeat center;\n        background-size: contain;\n}\n.items .second p input[data-v-ca25fc24] {\n        width: 1.6rem;\n        border-bottom: 1px #eee solid;\n        line-height: 0.93333rem;\n        text-align: center;\n}\n.items .on[data-v-ca25fc24] {\n    color: #306bce;\n}\n.items .on i[data-v-ca25fc24] {\n      display: block;\n      width: 0.4rem;\n      height: 0.4rem;\n      background: url("+i(t("rNPe"))+") no-repeat center;\n      background-size: contain;\n}\n.items p[data-v-ca25fc24] {\n    height: 1.2rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    padding: 0 0.4rem;\n}\n.btn[data-v-ca25fc24] {\n  background: #3069CF;\n  color: #fff;\n  font-size: 0.4rem;\n  width: 60%;\n  height: 1.06667rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  margin: 0.53333rem auto;\n  border-radius: 0.53333rem;\n}\n",""])},uPwl:function(n,e,t){var i=t("BCkX");"string"==typeof i&&(i=[[n.i,i,""]]),i.locals&&(n.exports=i.locals);t("rjj0")("381c5356",i,!0,{})}});