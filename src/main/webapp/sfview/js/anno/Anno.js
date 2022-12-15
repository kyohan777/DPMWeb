import Text from"/sfview/js/anno/Text.js";import ShapeArray from"/sfview/js/anno/ShapeArray.js";import AnnoToolbar from"/sfview/js/anno/AnnoToolbar.js";import Handler from"/sfview/js/anno/Handler.js";import SelectHandler from"/sfview/js/anno/SelectHandler.js";import CreateHandler from"/sfview/js/anno/CreateHandler.js";import PolylineHandler from"/sfview/js/anno/PolylineHandler.js";import Tracker from"/sfview/js/anno//Tracker.js";import RectTracker from"/sfview/js/anno/RectTracker.js";import LineTracker from"/sfview/js/anno/LineTracker.js";import Factory from"/sfview/js/anno/Factory.js";export default class Anno{static canvas;static ctx;static shapes;static ratio=1;static sfimg;static sfwork;static curHandler;static tracker;static json=null;static page=0;static $view;static checker=Anno.createSvg('<path clip-rule="evenodd" d="M21.652,3.211c-0.293-0.295-0.77-0.295-1.061,0L9.41,14.34  c-0.293,0.297-0.771,0.297-1.062,0L3.449,9.351C3.304,9.203,3.114,9.13,2.923,9.129C2.73,9.128,2.534,9.201,2.387,9.351  l-2.165,1.946C0.078,11.445,0,11.63,0,11.823c0,0.194,0.078,0.397,0.223,0.544l4.94,5.184c0.292,0.296,0.771,0.776,1.062,1.07  l2.124,2.141c0.292,0.293,0.769,0.293,1.062,0l14.366-14.34c0.293-0.294,0.293-0.777,0-1.071L21.652,3.211z" fill-rule="evenodd"/>');static createSvg(svg){const img=new Image;img.src="data:image/svg+xml;base64,"+btoa(`<svg xmlns="http://www.w3.org/2000/svg" height="24" width="24" >${svg}</svg>`);return img}static init($view){this.canvas=document.getElementById("sfanno");this.sfimg=document.getElementById("sfimg");this.sfwork=document.getElementById("sfwork");this.$view=$view;Text.initTextArea();this.shapes=new ShapeArray;this.setEvent(this.canvas);$("canvas#sfanno, ul.show").show();this.tracker=new Tracker;this.tracker.initTracker(new RectTracker,new LineTracker);this.tracker.initHandler(new SelectHandler,new CreateHandler,new PolylineHandler);this.curHandler=this.tracker.selectHandler;AnnoToolbar.init($view);AnnoToolbar.toolbarUpdateHandler();$view.on("fit",(function(fit){Anno.syncAnnotation();Anno.drawShapes()}));$view.on("zoom",(function(ratio){Anno.syncAnnotation();Anno.drawShapes()}));$view.on("rotate",(function(angle){Anno.initCtx();Anno.drawShapes()}))}static initCtx(){const ratio=Anno.$view.getRatio();const offset=Anno.isWorkCanvas()?Anno.$view.getMargin():0;const canvas=Anno.canvas;this.ratio=ratio;Anno.ctx=canvas.getContext("2d");Anno.ctx.fillStyle="#00000000";Anno.ctx.strokeStyle="#00000000";const pixelRatio=window.devicePixelRatio??1;canvas.width=canvas.clientWidth*pixelRatio;canvas.height=canvas.clientHeight*pixelRatio;Anno.ctx.translate(offset+.5,offset+.5);Anno.ctx.scale(pixelRatio*ratio,pixelRatio*ratio);if(Anno.isWorkCanvas()){const angle=Anno.$view.getAngle();const[width,height]=Anno.$view.getImageSize();Anno.ctx.rotate(angle*Math.PI/180);switch(angle){case 0:Anno.ctx.translate(-Anno.sfwork.scrollLeft/ratio,-Anno.sfwork.scrollTop/ratio);break;case 90:Anno.ctx.translate(-Anno.sfwork.scrollTop/ratio,Anno.sfwork.scrollLeft/ratio-height);break;case 180:Anno.ctx.translate(Anno.sfwork.scrollLeft/ratio-width,Anno.sfwork.scrollTop/ratio-height);break;case 270:Anno.ctx.translate(Anno.sfwork.scrollTop/ratio-width,-Anno.sfwork.scrollLeft/ratio);break}}}static transEventPos(x,y){if(Anno.isWorkCanvas()){const angle=Anno.$view.getAngle();const ratio=Anno.ratio;const[width,height]=Anno.$view.getImageSize();const margin=this.$view.getMargin()/ratio;let tx,ty;switch(angle){case 0:[tx,ty]=[x+Anno.sfwork.scrollLeft/ratio-margin,y+Anno.sfwork.scrollTop/ratio-margin];break;case 90:[tx,ty]=[y+Anno.sfwork.scrollTop/ratio-margin,-x-(Anno.sfwork.scrollLeft/ratio-height-margin)];break;case 180:[tx,ty]=[-x-(Anno.sfwork.scrollLeft/ratio-width-margin),-y-(Anno.sfwork.scrollTop/ratio-height-margin)];break;case 270:[tx,ty]=[-y-(Anno.sfwork.scrollTop/ratio-width-margin),x+Anno.sfwork.scrollLeft/ratio-margin];break}return[tx,ty]}return[x,y]}static drawShapes(){Anno.shapes.redraw()}static getLocation(){const rect=Anno.canvas.getBoundingClientRect();return[rect.x,rect.y]}static getCreatedShape(){return Handler.shape}static setJson(json,page){this.shapes.length=0;this.json=json;this.page=page;let pageJson=new Object;pageJson.shapes=new Array;for(let i=0;i<json.length;i++){if(page==json[i].index){pageJson=json[i];break}}pageJson.shapes.forEach((x=>{const id=(x.type=="annotation"?"annotation.":"")+x.name;let shape=Factory.create(id,0,0);if(shape){shape.setJson(x)}}));Anno.syncAnnotation()}static getJson(){const shapes=new Array;this.shapes.forEach((x=>{shapes.push(x.getJson())}));const pageJson=new Object;pageJson.index=this.page;pageJson.shapes=shapes;const json=this.json??new Array;for(let i=0;i<json.length;i++){if(pageJson.index==json[i].index){json[i]=pageJson;return json}}json.push(pageJson);json.sort(((a,b)=>a.index>b.index?a:b));return json}static getHandler(x,y){const handler=this.shapes.findLast((function(shape){const handler=shape.tracker.getHandler(shape,x,y);if(handler){return handler}return null}));return handler}static setEvent(canvas){let shapes=Anno.shapes;canvas.addEventListener("mousemove",(function(e){if(!AnnoToolbar.isEditMode()){e.preventDefault();const event=new CustomEvent("mousemove");event.offsetX=e.offsetX;event.offsetY=e.offsetY;event.buttons=e.buttons;event.clientX=e.clientX;event.clientY=e.clientY;Anno.sfimg.dispatchEvent(event);return}let[x,y]=Anno.transEventPos(e.offsetX/Anno.ratio,e.offsetY/Anno.ratio);if(e.buttons&1){const handler=Anno.curHandler;handler.calcStep(Handler.shape,x,y);shapes.forEachSelected((shape=>handler.applyStep(shape)));shapes.redraw();if(handler instanceof SelectHandler){handler.redraw()}}else{const handler=Anno.getHandler(x,y);canvas.style.cursor=handler?handler.getCursor():"auto"}}));canvas.addEventListener("mousedown",(function(e){if(!AnnoToolbar.isEditMode()){e.preventDefault();const event=new CustomEvent("mousedown");event.offsetX=e.offsetX;event.offsetY=e.offsetY;event.buttons=e.buttons;event.clientX=e.clientX;event.clientY=e.clientY;Anno.sfimg.dispatchEvent(event);return}const handler=Anno.curHandler;let[x,y]=Anno.transEventPos(e.offsetX/Anno.ratio,e.offsetY/Anno.ratio);handler.initStep(x,y,e.shiftKey)}));canvas.addEventListener("mouseup",(function(e){if(!AnnoToolbar.isEditMode()){e.preventDefault();const event=new CustomEvent("mouseup");event.offsetX=e.offsetX;event.offsetY=e.offsetY;event.buttons=e.buttons;Anno.sfimg.dispatchEvent(event);return}const handler=Anno.curHandler;handler.endStep();shapes.redraw();Anno.curHandler=Anno.tracker.selectHandler}));canvas.addEventListener("dblclick",(e=>{if(!AnnoToolbar.isEditMode()){e.preventDefault();const event=new CustomEvent("dblclick");event.offsetX=e.offsetX;event.offsetY=e.offsetY;event.buttons=e.buttons;Anno.sfimg.dispatchEvent(event);return}}));canvas.addEventListener("wheel",(e=>{if(Anno.isWorkCanvas()){e.preventDefault();Anno.sfwork.scrollBy(e.deltaX,e.deltaY)}}));Anno.sfwork.addEventListener("scroll",(e=>{if(Anno.isWorkCanvas()){Anno.initCtx();shapes.redraw()}}));document.addEventListener("keydown",(function(e){if(e.target==document.body&&(e.key=="Delete"||e.key=="Backspace")&&!e.repeat){shapes.removeSelected();shapes.redraw()}}))}static clear(){let shapes=this.shapes;shapes.clear();shapes.redraw()}static syncAnnotation(){Anno.$view.setAnnoArea();Anno.initCtx()}static isWorkCanvas(){return this.canvas.parentElement==this.sfwork}static drawMask(shape,x,y,width,height,color){const o=Factory.create("masking."+shape,x,y);if(o){o.width=width;o.height=height;if(color){o.fillStyle=color}}return o}static drawShape(shape,x,y,width,height,color){const o=Factory.create("annotation."+shape,x,y);if(o){o.width=width;o.height=height;if(color){o.fillStyle=color}}return o}static drawLine(shape,x0,y0,x1,y1,color){const o=Factory.create("annotation."+shape,x0,y0);if(o){if(shape=="line"||shape=="arrow"){o.ex=x1;o.ey=y1}else{o.add(x1,y1)}if(color){o.strokeStyle=color}}return o}}