import Rectangle from"/sfview/js/anno/Rectangle.js";export default class Stamp extends Rectangle{text;constructor(x,y,width,height,text){super(x,y,width,height,"#ff0000",null,(function(ctx){ctx.strokeStyle=this.fillStyle;ctx.lineWidth=20;ctx.strokeRect(this.x,this.y,this.width,this.height);ctx.font=`700 ${Math.round(this.height*.6)}px Times New Roman`;ctx.textAlign="center";ctx.fillText(this.text,this.x+this.width/2,this.y+this.height*.7,this.width-ctx.lineWidth*2)}));this.text=text;this.lineWidth=null}setJson(json){super.setJson(json);this.text=json.style.text}getJson(){const json=super.getJson();json.style.text=this.text;return json}}