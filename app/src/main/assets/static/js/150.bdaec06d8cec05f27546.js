webpackJsonp([150],{"2Ixq":function(t,n,e){var i=e("ygrW");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);e("rjj0")("078aaeba",i,!0,{})},"6/vk":function(t,n,e){"use strict";function i(t){e("2Ixq")}Object.defineProperty(n,"__esModule",{value:!0});var a=e("pKZN"),s=(a.a,{components:{back:a.a},data:function(){return{info:null,id:null,rejectId:null,reason:[],RejectReasonIds:[],user:this.$store.state.userInfo.Data,showConfirm:!1,medList:[],DrType:0}},created:function(){this.pull(),this.pullReject()},methods:{pull:function(){var t=this;this.medList=[],this.id=this.$route.query.id,this.rejectId=this.$route.query.rejectId,this.id&&this.$post("/Prescription/Prescription/GetPrescription",{id:this.id}).then(function(n){t.info=n,t.RejectReasonIds=n.RejectReasonId?n.RejectReasonId.split(","):[];var e=n.ListDetails.length/5;if(e>1)for(var i=0;i<e;i++)t.medList.push(n.ListDetails.slice(5*i,5*(i+1)));else t.medList[0]=n.ListDetails}),this.rejectId&&this.$post("/Prescription/Prescription/GetPrescription",{id:this.rejectId}).then(function(n){t.info=n,t.RejectReasonIds=n.RejectReasonId?n.RejectReasonId.split(","):[];var e={};e.PatientInfo={PatientId:n.PatientId,PatientName:n.PatientName,PatientSex:n.PatientSex,Birthday:n.Birthday,Age:n.Age},e.DoctorPatientRelation={DrPatientId:n.DrPatientId},t.$store.dispatch("pushpInfo",e),t.$store.dispatch("pushpatient",e.PatientInfo)}),this.$get("PlatFormAPI/Doctor/QueryDoctorInfoByDrId",{DrId:this.user.User.DoctorId}).then(function(n){t.DrType=n.DrType})},withdraw:function(){var t=this;this.$post("Prescription/Prescription/RevokePrescription",{PrescriptionId:this.$route.query.id,DoctorId:this.user.User.DoctorId}).then(function(n){t.$toast("撤回成功"),t.pull()})},pullReject:function(){var t=this;this.$get("PlatFormAPI/Category/QueryItemByCategoryId",{CategoryId:37}).then(function(n){t.reason=n})},toReset:function(){this.$store.dispatch("pushDiagnoses2",null),this.$store.dispatch("pushupList",[]),this.$store.dispatch("pushpatient",{PatientName:this.info.PatientName,PatientSex:this.info.PatientSex,PatientId:this.info.PatientId,Age:this.info.Age}),this.$store.dispatch("pushpInfo",{ApplicantName:this.info.ApplicantName,ApplicantId:this.info.ApplicantId,PatientInfo:{PatientName:this.info.PatientName,PatientSex:this.info.PatientSex,PatientId:this.info.PatientId,Age:this.info.Age}}),this.$router.push("/pharmacy?from=p")},modify:function(){this.$store.dispatch("pushpullFlag",0),this.$store.dispatch("pushpatient",{PatientName:this.info.PatientName,PatientSex:this.info.PatientSex,PatientId:this.info.PatientId,Age:this.info.Age}),this.$store.dispatch("pushpInfo",{ApplicantName:this.info.ApplicantName,ApplicantId:this.info.ApplicantId,PatientInfo:{PatientName:this.info.PatientName,PatientSex:this.info.PatientSex,PatientId:this.info.PatientId,Age:this.info.Age}}),this.$router.push("/prescription?rejectId="+this.info.Id)},reject:function(){this.$bridge.callhandler("preReject")},accept:function(){this.$bridge.callhandler("preAccept")}}}),o=function(){var t=this,n=t.$createElement,e=t._self._c||n;return t.info&&t.reason?e("div",{staticClass:"pdt"},[e("div",{staticClass:"title"},[e("back"),t._v(" "),e("span",{staticClass:"name"},[t._v("处方单")]),t._v(" "),e("a")],1),t._v(" "),e("p",{staticClass:"status"},[e("span",[e("a",[t._v("处方状态："+t._s(t._f("PreStatus")(t.info.Status)))]),t._v(" "),1==t.info.CanRevoke&&3!=t.DrType?e("a",{staticClass:"butt",on:{click:function(n){t.showConfirm=!0}}},[t._v("申请撤回")]):t._e()]),t._v(" "),2==t.info.Status?e("span",{staticClass:"justc"},[t._v("\n                驳回理由："),t.info.RejectReason?e("a",[t._v(t._s(t.info.RejectReason)+",")]):e("a"),t._v(" "),e("a",[t._v(t._s(t.info.AuditRemark))])]):t._e()]),t._v(" "),e("swiper",{attrs:{height:720+100*(t.medList[0].length-3>0?t.medList[0].length-3:0)+"px","dots-position":"center","show-dots":t.medList.length>1}},t._l(t.medList,function(n,i){return e("swiper-item",{key:i,staticClass:"black"},[e("p",{staticClass:"preTit"},[e("i"),t._v(" "),e("span",{staticClass:"name"},[t._v("新起点互联网医院处方笺")]),t._v(" "),e("span",{staticClass:"tip"},[t._v("普通药品处方")])]),t._v(" "),e("div",{staticClass:"info"},[e("div",{staticClass:"card"},[e("p",[e("span",[t._v("费别：")]),t._v(" "),e("span",[t._v(t._s(t.info.CostType))])]),t._v(" "),e("p",[e("span",[t._v("医疗证号：")]),t._v(" "),e("span",[t._v(t._s(t.info.MedicalNo))])]),t._v(" "),e("p",[e("span",[t._v("处方编号：")]),t._v(" "),e("span",[t._v(t._s(t.info.No)),t.medList.length>1?e("a",[t._v(t._s(i+1))]):t._e()])])]),t._v(" "),e("div",{staticClass:"card"},[e("p",[e("span",[t._v("姓名：")]),t._v(" "),e("span",[t._v(t._s(t.info.PatientName))])]),t._v(" "),e("p",[e("span",[t._v("性别：")]),t._v(" "),e("span",[t._v(t._s(t._f("toSex")(t.info.PatientSex)))])]),t._v(" "),e("p",[e("span",[t._v("年龄：")]),t._v(" "),e("span",[t._v(t._s(t.info.Age)+"岁")])])]),t._v(" "),e("div",{staticClass:"card"},[e("p",[e("span",[t._v("门诊病历号：")]),t._v(" "),e("span",[t._v(t._s(t.info.MedicalRecordNo))])]),t._v(" "),e("p",[e("span",[t._v("科别：")]),t._v(" "),e("span",[t._v(t._s(t.info.Department))])]),t._v(" "),e("p")]),t._v(" "),e("div",{staticClass:"card card2"},[e("p",[e("span",[t._v("主诊医生：")]),t._v(" "),e("span",[t._v(t._s(t.info.DoctorName))])]),t._v(" "),e("p",[e("span",[t._v("开具日期：")]),t._v(" "),e("span",[t._v(t._s(t.info.PrescribeTime.slice(0,10)))])])]),t._v(" "),e("div",{staticClass:"card"},[e("p",[e("span",[t._v("住址：")]),t._v(" "),e("span",[t._v(t._s(t.info.AreaName)+t._s(t.info.Region))])]),t._v(" "),e("p",[e("span",[t._v("电话：")]),t._v(" "),e("span",[t._v(t._s(t.info.Mobile))])])]),t._v(" "),e("div",{staticClass:"card card1"},[e("p",[e("span",[t._v("临床诊断：")]),t._v(" "),e("span",[t._v(t._s(t.info.Diagnoses))])])])]),t._v(" "),e("div",{staticClass:"med"},[e("p",{staticClass:"tit"},[t._v("Rp")]),t._v(" "),t._l(n,function(n,i){return e("div",{key:i,staticClass:"list"},[e("p",[e("span",[t._v(t._s(i+1)+"、"+t._s(n.Name)+" "),n.Brand?e("span",[t._v("("+t._s(n.Brand)+")")]):t._e()]),t._v(" "),e("span",{staticClass:"spec"},[t._v(t._s(n.Specification)+"  x"+t._s(n.Quantity)+t._s(n.PackageUnit))])]),t._v(" "),e("p",[t._v("Sig:"+t._s(n.UsageTime)+t._s(n.UsageMethod)+" 每次"+t._s(0==n.Dosage?"":n.Dosage)+t._s(n.DosageUnit)+" "+t._s(n.DayDosage)+" ")]),t._v(" "),e("p",[n.DosageDays?e("span",[t._v("用药"+t._s(n.DosageDays))]):t._e()]),t._v(" "),e("p",[t._v(t._s(n.Remark))])])})],2),t._v(" "),e("div",{staticClass:"sign"},[e("p",[e("span",[t._v("医师："),t.info.DoctorSignImgUrl?e("img",{attrs:{src:t.info.DoctorSignImgUrl}}):t._e()]),t._v(" "),e("span",[t._v("药品金额：￥"+t._s(t.info.TotalPrice))])]),t._v(" "),e("p",[e("span",[t._v("审核药师："),t.info.ApothecarySignImgUrl?e("img",{attrs:{src:t.info.ApothecarySignImgUrl}}):t._e()]),t._v(" "),e("span",[t._v("调配药师：")]),t._v(" "),e("span",[t._v("核对、发药药师：")])])]),t._v(" "),e("p",{staticClass:"bottom"},[e("span",[t._v(t._s(t.info.PrescribeTime))]),t._v(" "),e("span",[t._v("处方开具"+t._s(1==t.info.ValidDays?"当":t.info.ValidDays)+"日有效")])]),t._v(" "),2==t.info.Status?e("p",{staticClass:"rejectReason"},[t._v("\n                    驳回理由："),t._l(t.RejectReasonIds,function(n,i){return e("span",{key:i},[t._v(t._s(t._f("Category")(n,t.reason))+",")])}),t._v(" "),e("span",[t._v(t._s(t.info.AuditRemark))])],2):t._e()])})),t._v(" "),t.$route.query.drtype?t._e():e("div",[2!=t.info.Status&&3!=t.info.Status||t.$route.query.from?t._e():e("p",{staticClass:"btn"},[e("span",{on:{click:t.modify}},[t._v("修改")]),t._v(" "),e("span",{on:{click:t.toReset}},[t._v("新建")])])]),t._v(" "),t.$route.query.btnType&&0==t.info.Status?e("div",[e("p",{staticClass:"btnb"},[e("span",{on:{click:t.reject}},[t._v("驳回")]),t._v(" "),e("span",{on:{click:t.accept}},[t._v("通过")])])]):t._e(),t._v(" "),e("confirm",{on:{"on-cancel":function(n){t.showConfirm=!1},"on-confirm":t.withdraw},model:{value:t.showConfirm,callback:function(n){t.showConfirm=n},expression:"showConfirm"}},[t._v("一旦撤回，该处方笺将作废，是否确定撤回？")])],1):e("div",[e("div",{staticClass:"title"},[e("back"),t._v(" "),e("span",{staticClass:"name"},[t._v("处方单")]),t._v(" "),e("a")],1)])},c=[],r={render:o,staticRenderFns:c},p=r,d=e("VU/8"),l=i,f=d(s,p,!1,l,"data-v-3162439c",null);n.default=f.exports},ygrW:function(t,n,e){n=t.exports=e("FZ+f")(!1),n.push([t.i,"\n.status[data-v-3162439c] {\n  background: #fff;\n  padding: 0 0.4rem;\n  border-bottom: 1px solid #ececec;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n  -webkit-flex-direction: column;\n          flex-direction: column;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  min-height: 1.06667rem;\n}\n.status span[data-v-3162439c] {\n    color: red;\n    font-size: 12px;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-pack: justify;\n    -webkit-justify-content: space-between;\n            justify-content: space-between;\n}\n.status span[data-v-3162439c]:first-child {\n      color: #333;\n      font-size: 14px;\n}\n.status span .butt[data-v-3162439c] {\n      padding: 0 0.26667rem;\n      height: 0.73333rem;\n      background: #306bce;\n      color: #fff;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-pack: center;\n      -webkit-justify-content: center;\n              justify-content: center;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      border-radius: 20px;\n}\n.status .justc[data-v-3162439c] {\n    display: block;\n    padding-bottom: 0.13333rem;\n}\n.preTit[data-v-3162439c] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-pack: justify;\n  -webkit-justify-content: space-between;\n          justify-content: space-between;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  background: #fff;\n  padding: 0 0.4rem;\n  min-height: 1.6rem;\n}\n.preTit .name[data-v-3162439c] {\n    font-size: 16px;\n}\n.preTit .tip[data-v-3162439c] {\n    font-size: 13px;\n    max-width: 2rem;\n    text-align: center;\n    border: 1px solid #000;\n    padding: 0.06667rem 0.06667rem;\n}\n.preTit i[data-v-3162439c] {\n    display: block;\n    width: 1.6rem;\n}\n.info[data-v-3162439c] {\n  padding: 0.4rem 0.4rem 0;\n  background: #fff;\n}\n.info .card[data-v-3162439c] {\n    font-size: 12px;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: justify;\n    -webkit-justify-content: space-between;\n            justify-content: space-between;\n    padding: 0.06667rem 0;\n}\n.info .card[data-v-3162439c]:first-child {\n      border-bottom: 1px solid #000;\n}\n.info .card[data-v-3162439c]:last-child {\n      border-bottom: 1px solid #000;\n}\n.info .card p[data-v-3162439c] {\n      min-width: 33%;\n      margin: 0 0.06667rem;\n}\n.info .card p span[data-v-3162439c]:first-child {\n        white-space: nowrap;\n}\n.info .card1 p[data-v-3162439c] {\n    max-width: 100%;\n}\n.info .card2[data-v-3162439c] {\n    -webkit-box-pack: start;\n    -webkit-justify-content: flex-start;\n            justify-content: flex-start;\n}\n.med[data-v-3162439c] {\n  background: #fff;\n  padding: 0.4rem;\n  min-height: 8rem;\n}\n.med .tit[data-v-3162439c] {\n    font-size: 23px;\n    font-weight: bold;\n}\n.med .list[data-v-3162439c] {\n    padding: 0.26667rem 0.4rem;\n    font-size: 14px;\n}\n.med .list p[data-v-3162439c] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-pack: justify;\n      -webkit-justify-content: space-between;\n              justify-content: space-between;\n}\n.med .list .spec[data-v-3162439c] {\n      display: block;\n      width: 2.4rem;\n}\n.sign[data-v-3162439c] {\n  background: #fff;\n  border-top: 1px solid #000;\n  border-bottom: 1px solid #000;\n  padding: 0 0.8rem;\n  height: 2.4rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-orient: vertical;\n  -webkit-box-direction: normal;\n  -webkit-flex-direction: column;\n          flex-direction: column;\n  -webkit-justify-content: space-around;\n          justify-content: space-around;\n  font-size: 13px;\n}\n.sign p[data-v-3162439c] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-pack: justify;\n    -webkit-justify-content: space-between;\n            justify-content: space-between;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.sign p span[data-v-3162439c] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      white-space: nowrap;\n}\n.sign p img[data-v-3162439c] {\n      width: 0.93333rem;\n      height: 0.93333rem;\n}\n.bottom[data-v-3162439c] {\n  padding: 0.26667rem 0.8rem 0.4rem;\n  font-size: 12px;\n  background: #fff;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-pack: justify;\n  -webkit-justify-content: space-between;\n          justify-content: space-between;\n}\n.rejectReason[data-v-3162439c] {\n  background: #fff;\n  padding: 0 0.4rem 0.4rem 0.4rem;\n}\n.btn[data-v-3162439c] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-justify-content: space-around;\n          justify-content: space-around;\n  background: #fff;\n  padding: 0.4rem;\n}\n.btn span[data-v-3162439c] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    height: 1.17333rem;\n    width: 3.33333rem;\n    background: #306bce;\n    color: #fff;\n    border-radius: 20px;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n}\n.btnb[data-v-3162439c] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-justify-content: space-around;\n          justify-content: space-around;\n  background: #fff;\n  padding: 0.4rem;\n}\n.btnb span[data-v-3162439c] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    height: 1.06667rem;\n    width: 3.33333rem;\n    border: 1px solid #376fce;\n    border-radius: 30px;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    color: #8c85ab;\n}\n.btnb span[data-v-3162439c]:last-child {\n      background: #376fce;\n      color: #fff;\n}\n",""])}});