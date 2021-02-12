(window.webpackJsonp=window.webpackJsonp||[]).push([[29],{83:function(e,t,n){"use strict";n.r(t),n.d(t,"frontMatter",(function(){return i})),n.d(t,"metadata",(function(){return p})),n.d(t,"rightToc",(function(){return l})),n.d(t,"default",(function(){return s}));var r=n(2),a=n(6),o=(n(0),n(94)),i={id:"live-templates",title:"Some Live Templates",sidebar_label:"Live Templates",slug:"/get-started/live-templates"},p={unversionedId:"get-started/live-templates",id:"get-started/live-templates",isDocsHomePage:!1,title:"Some Live Templates",description:"List of templates that may help development.",source:"@site/docs/get-started/live-templates.md",slug:"/get-started/live-templates",permalink:"/reasonml-idea-plugin/docs/get-started/live-templates",editUrl:"https://github.com/reasonml-editor/reasonml-idea-plugin/edit/master/website/docs/get-started/live-templates.md",version:"current",sidebar_label:"Live Templates",sidebar:"someSidebar",previous:{title:"Supported Project Types",permalink:"/reasonml-idea-plugin/docs/get-started/project-types"},next:{title:"FAQ",permalink:"/reasonml-idea-plugin/docs/get-started/faq"}},l=[],c={rightToc:l};function s(e){var t=e.components,n=Object(a.a)(e,["components"]);return Object(o.b)("wrapper",Object(r.a)({},c,n,{components:t,mdxType:"MDXLayout"}),Object(o.b)("p",null,"List of templates that may help development."),Object(o.b)("p",null,"abbrevation: ",Object(o.b)("inlineCode",{parentName:"p"},"jsp"),", description: ",Object(o.b)("inlineCode",{parentName:"p"},"create a jsProps in wrapReasonForJs")),Object(o.b)("pre",null,Object(o.b)("code",Object(r.a)({parentName:"pre"},{}),"~$NAME$=jsProps##$NAME$,$END$\n")),Object(o.b)("p",null,"abbreviation: ",Object(o.b)("inlineCode",{parentName:"p"},"style"),", description: ",Object(o.b)("inlineCode",{parentName:"p"},"create a style module")),Object(o.b)("pre",null,Object(o.b)("code",Object(r.a)({parentName:"pre"},{}),"module Styles = {\n    open Css;\n    let $VAR$ = style([\n        $END$\n    ]);\n};\n")),Object(o.b)("p",null,"abbreviation: ",Object(o.b)("inlineCode",{parentName:"p"},"comp"),", description: ",Object(o.b)("inlineCode",{parentName:"p"},"create a reason component")),Object(o.b)("pre",null,Object(o.b)("code",Object(r.a)({parentName:"pre"},{}),'let component = ReasonReact.statelessComponent("$NAME$");\n\nlet make = (~$PROP$, _children) => {...component, render: _self => <div />};\n$END$\n/*\n JS interop\n expose an helper for js - can be deleted when no more used by javascript code\n */\nlet jsComponent =\n  ReasonReact.wrapReasonForJs(~component, jsProps =>\n    make(~$PROP$=jsProps##$PROP$, [||])\n  );\n')))}s.isMDXComponent=!0},94:function(e,t,n){"use strict";n.d(t,"a",(function(){return m})),n.d(t,"b",(function(){return d}));var r=n(0),a=n.n(r);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function p(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function l(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var c=a.a.createContext({}),s=function(e){var t=a.a.useContext(c),n=t;return e&&(n="function"==typeof e?e(t):p(p({},t),e)),n},m=function(e){var t=s(e.components);return a.a.createElement(c.Provider,{value:t},e.children)},u={inlineCode:"code",wrapper:function(e){var t=e.children;return a.a.createElement(a.a.Fragment,{},t)}},b=a.a.forwardRef((function(e,t){var n=e.components,r=e.mdxType,o=e.originalType,i=e.parentName,c=l(e,["components","mdxType","originalType","parentName"]),m=s(n),b=r,d=m["".concat(i,".").concat(b)]||m[b]||u[b]||o;return n?a.a.createElement(d,p(p({ref:t},c),{},{components:n})):a.a.createElement(d,p({ref:t},c))}));function d(e,t){var n=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var o=n.length,i=new Array(o);i[0]=b;var p={};for(var l in t)hasOwnProperty.call(t,l)&&(p[l]=t[l]);p.originalType=e,p.mdxType="string"==typeof e?e:r,i[1]=p;for(var c=2;c<o;c++)i[c]=n[c];return a.a.createElement.apply(null,i)}return a.a.createElement.apply(null,n)}b.displayName="MDXCreateElement"}}]);