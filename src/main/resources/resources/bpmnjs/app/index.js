
import $ from 'jquery';
import BpmnModeler from 'bpmn-js/lib/Modeler';
//import propertiesPanelModule from '../resources/properties-panel';
import propertiesPanelModule from 'bpmn-js-properties-panel';
import propertiesProviderModule from '../resources/properties-panel/provider/activiti';
import activitiModdleDescriptor from '../resources/activiti.json';
import customTranslate from '../resources/customTranslate/customTranslate';
import customControlsModule from '../resources/customControls';
import tools from '../resources/tools'
import diagramXML from '../resources/newDiagram.bpmn';
const proHost = window.location.protocol + "//" + window.location.host;
const href = window.location.href.split("bpmnjs")[0];
const key = href.split(window.location.host)[1];
const publicurl = proHost + key;

// 添加翻译组件
var customTranslateModule = {
    translate: ['value', customTranslate]
};
var ColorJson = [{
    'name': 'test1',
    'stroke': 'green',
    'fill': 'yellow'
}, {
    'name': 'test2',
    'stroke': 'blue',
    'fill': 'red'
}]

var container = $('#js-drop-zone');
var canvas = $('#js-canvas');
var bpmnModeler = new BpmnModeler({
    container: canvas,
    propertiesPanel: {
        parent: '#js-properties-panel'
    },
    additionalModules: [
        propertiesPanelModule,
        propertiesProviderModule,
        customControlsModule,
        customTranslateModule
    ],
    moddleExtensions: {
        activiti:activitiModdleDescriptor
    }
});
container.removeClass('with-diagram');
// 判断浏览器支持程度
if (!window.FileList || !window.FileReader) {
    window.alert('请使用谷歌、火狐、IE10+浏览器');
} else {
    tools.registerFileDrop(container, tools.createDiagram(diagramXML, bpmnModeler, container));
}


$(function () {
    // 创建bpmn
    var param = tools.getUrlParam(window.location.href)
        $('.item').show()
    if (param.type === 'addBpmn') {
        tools.createDiagram(diagramXML, bpmnModeler, container);
    } else if (param.type === 'lookBpmn') { //编辑bpmn
        $('.item').hide()
        $('.download').show()
        const Id = param.deploymentFileUUID || '6d4af2dc-bab0-11ea-b584-3cf011eaafca'
        const Name=param.deploymentName || 'String.bpmn'
        //加载后台方法获取xml
        var param={
            "deploymentId":Id,
            "resourceName":Name
        }
        $.ajax({
            url: publicurl+'processDefinition/getDefinitionXML',
            type: 'GET',
            data: param,
            dataType:'text',
            success: function (result) {
                var newXmlData = result
                tools.createDiagram(newXmlData, bpmnModeler, container);
                setTimeout(function () {
                    for (var i in ColorJson) {
                        tools.setColor(ColorJson[i],bpmnModeler)
                    }
                }, 200)
            },
            error: function (err) {
                console.log(err)
            }
        });

    }
    // 点击新增
    $('#js-download-diagram').on("click", function () {
       tools.syopen('alert')
    })

    // 点击取消
    $('.cancel').on("click",function () {
        tools.syhide('alert')
    })
    // 点击确定
    $('#sure').on('click',function(){
        const text=$("#deploymentName").val()
        tools.saveBpmn(bpmnModeler,text)
    })



    // 点击下载
    $("#saveBpmn").on("click", function () {
        tools.downLoad(bpmnModeler)
    })
    // 点击上传
    $("#uploadFile").on("change", function () {
        tools.upload(bpmnModeler,container)
    })
});
