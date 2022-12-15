export default class TextDecoration{underline;lineThrough;font;constructor(font,underline=false,lineThrough=false){this.font=font;this.underline=underline;this.lineThrough=lineThrough}applyStyle(style){let td="";if(this.underline){td+="underline"}if(this.lineThrough){if(this.underline){td+=" "}td+="line-through"}style.textDecoration=td}#calcLine(ctx,text,x){let width=ctx.measureText(text).width;switch(ctx.textAlign){case"center":x-=width/2;break;case"end":x-=width;break}return[x,width]}#drawLine(ctx,x,y,width){ctx.moveTo(x,y);ctx.lineTo(x+width,y)}fillText(ctx,text,x,y){ctx.fillText(text,x,y);let width;if(this.underline){[x,width]=this.#calcLine(ctx,text,x);this.#drawLine(ctx,x,y+ctx.lineWidth,width)}if(this.lineThrough){if(typeof width=="undefined"){[x,width]=this.#calcLine(ctx,text,x)}this.#drawLine(ctx,x,y-this.font.size*.32,width)}}setJson(json){this.underline=json.under;this.lineThrough=json.cancel}}