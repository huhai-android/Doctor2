webpackJsonp([93],{E7ZW:function(t,n,e){"use strict";function i(t){e("bwQT")}Object.defineProperty(n,"__esModule",{value:!0});var a=(e("mtWM"),e("QmSG"),e("8pLc")),s=e("pKZN"),o=(a.a,s.a,{data:function(){return{span:2,list:[],temp:!0,idx:0,moreList:null,flag:!1,page:1,patient:this.$store.state.patient,patientName:""}},components:{noData:a.a,back:s.a},beforeDestroy:function(){window.onscroll=null},mounted:function(){var t=1,n=this;document.title=this.$route.meta.title,window.onscroll=function(){(document.documentElement.scrollTop||document.body.scrollTop)+(document.documentElement.clientHeight||document.body.clientHeight)==(document.documentElement.scrollHeight||document.body.scrollHeight)&&0>t&&this.idx>0&&(t=1+t,this.scroller=t,n.fn(t))}},created:function(){this.fn(1),this.patientName=this.patient.PatientName||"",console.log(this.patient)},methods:{fn:function(t){var n=this;this.temp&&(this.flag=!1,this.page=t,this.$post("Prescription/Prescription/GetPrescriptionListByPatientId",{ApplicantId:this.patient.AccountId,PatientId:this.patient.PatientId,PageIndex:t,PageSize:10}).then(function(t){0!=t.ReturnData.length&&(n.list=n.list.concat(t.ReturnData)),n.moreList=t.ReturnData||[],n.idx++,n.flag=!0}))}}}),r=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"pdt"},[e("div",{staticClass:"title"},[e("back"),t._v(" "),e("span",{staticClass:"name"},[t._v(t._s(t.patientName)+"患者的处方")]),t._v(" "),e("a")],1),t._v(" "),2==t.span?e("div",t._l(t.list,function(n,i){return e("div",{key:i,class:[0==i?"divdib":"divdib2"],on:{click:function(e){t.$router.push("/PrescriptionLlist?id="+n.Id)}}},[e("div",{staticClass:"MyPrescription_div"},[e("p",[e("span",{staticClass:"MyPrescription_span2"},[t._v("诊断：")]),e("span",{staticClass:"MyPrescription_spano"},[t._v(t._s(n.Diagnoses))])]),t._v(" "),e("p",[e("span",{staticClass:"MyPrescription_span"},[t._v("开方医师："+t._s(n.DoctorName))])]),t._v(" "),e("p",{staticClass:"MyPrescription_span"},[t._v("开方时间："+t._s(n.PrescribeTime))])])])})):t._e(),t._v(" "),(!t.list||0==t.list.length)&&t.idx>0?e("no-data",{attrs:{txt:"暂无药物信息"}}):t._e(),t._v(" "),t.page>1&&!t.moreList.length?e("p",{staticClass:"reload",staticStyle:{background:"#f3f3f3"}},[t._v("已显示全部内容")]):t._e(),t._v(" "),e("p",{directives:[{name:"show",rawName:"v-show",value:t.page>1&&!t.flag,expression:"(page > 1) && !flag"}],staticClass:"reload",staticStyle:{background:"#f3f3f3"}},[t._v("正在加载")])],1)},d=[],c={render:r,staticRenderFns:d},l=c,p=e("VU/8"),m=i,f=p(o,l,!1,m,"data-v-7842f0dd",null);n.default=f.exports},Ridd:function(t,n,e){n=t.exports=e("FZ+f")(!1),n.push([t.i,"\n.doctor_text[data-v-7842f0dd] {\n  text-align: center;\n  background: white;\n  margin-top: 0.26667rem;\n  height: 6.66667rem;\n  width: 100%;\n}\n.noData[data-v-7842f0dd] {\n  margin-top: 0.13333rem;\n  font-size: 0.37333rem;\n}\n.divdib[data-v-7842f0dd] {\n  padding-top: 0.26667rem;\n}\n.divdib2[data-v-7842f0dd] {\n  padding-top: 0.53333rem;\n}\n.MyPrescription_ul[data-v-7842f0dd] {\n  border-top: 0.02667rem solid #f7f7f7;\n}\n.MyPrescription_ul > li[data-v-7842f0dd] {\n  float: left;\n  background: white;\n  height: 1.06667rem;\n  line-height: 1.06667rem;\n  color: #333333;\n  width: 50%;\n  text-align: center;\n  font-size: 0.37333rem;\n}\n.MyPrescription_ul > li > .actice[data-v-7842f0dd] {\n  padding-bottom: 0.26667rem;\n  border-bottom: 0.05333rem solid #3069ee;\n}\n.MyPrescription_ul > li > .actice2[data-v-7842f0dd] {\n  padding-bottom: 0.26667rem;\n}\n.MyPrescription_div[data-v-7842f0dd] {\n  width: 90%;\n  margin: 0 auto;\n  border-radius: 0.26667rem;\n  padding-bottom: 0.26667rem;\n  background: white;\n}\n.MyPrescription_div p img[data-v-7842f0dd] {\n    width: 0.66667rem;\n    height: 0.66667rem;\n    vertical-align: middle;\n    position: absolute;\n    right: 0.8rem;\n    margin-top: -0.2rem;\n}\n.MyPrescription_div > p[data-v-7842f0dd] {\n  margin-left: 0.53333rem;\n  padding-top: 0.26667rem;\n}\n.MyPrescription_span[data-v-7842f0dd] {\n  font-size: 0.37333rem;\n  color: #666666;\n}\n.MyPrescription_span2[data-v-7842f0dd] {\n  font-size: 0.37333rem;\n  color: black;\n}\n.MyPrescription_spano[data-v-7842f0dd] {\n  font-size: 0.53333rem;\n  color: black;\n}\n",""])},bwQT:function(t,n,e){var i=e("Ridd");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);e("rjj0")("0c4b810d",i,!0,{})}});