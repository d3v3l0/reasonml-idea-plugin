(window.webpackJsonp=window.webpackJsonp||[]).push([[14],{153:function(e,t,n){"use strict";n.r(t),t.default=n.p+"assets/images/run_ide-16ae610e9fcaeec46e330232b03afed4.png"},67:function(e,t,n){"use strict";n.r(t),n.d(t,"frontMatter",(function(){return l})),n.d(t,"metadata",(function(){return a})),n.d(t,"rightToc",(function(){return c})),n.d(t,"default",(function(){return p}));var r=n(2),i=n(6),o=(n(0),n(96)),l={id:"plugin-development",title:"Plugin Development",sidebar_label:"Plugin Development",slug:"/contributing/plugin-development"},a={unversionedId:"contributing/plugin-development",id:"contributing/plugin-development",isDocsHomePage:!1,title:"Plugin Development",description:"Plugin Development",source:"@site/docs/contributing/plugin-development.md",slug:"/contributing/plugin-development",permalink:"/reasonml-idea-plugin/docs/contributing/plugin-development",editUrl:"https://github.com/reasonml-editor/reasonml-idea-plugin/edit/master/website/docs/contributing/plugin-development.md",version:"current",sidebar_label:"Plugin Development",sidebar:"someSidebar",previous:{title:"How to Contribute",permalink:"/reasonml-idea-plugin/docs/contributing"},next:{title:"Plugin Architecture",permalink:"/reasonml-idea-plugin/docs/contributing/plugin-architecture"}},c=[{value:"Prepare your environment",id:"prepare-your-environment",children:[]},{value:"Import",id:"import",children:[]},{value:"Run",id:"run",children:[]}],u={rightToc:c};function p(e){var t=e.components,l=Object(i.a)(e,["components"]);return Object(o.b)("wrapper",Object(r.a)({},u,l,{components:t,mdxType:"MDXLayout"}),Object(o.b)("h1",{id:"plugin-development"},"Plugin Development"),Object(o.b)("h2",{id:"prepare-your-environment"},"Prepare your environment"),Object(o.b)("ol",null,Object(o.b)("li",{parentName:"ol"},"install the plugin prerequisites (from ",Object(o.b)("a",Object(r.a)({parentName:"li"},{href:"http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/prerequisites.html"}),"intellij documentation"),")",Object(o.b)("ol",{parentName:"li"},Object(o.b)("li",{parentName:"ol"},Object(o.b)("inlineCode",{parentName:"li"},"git clone https://github.com/JetBrains/intellij-community.git")),Object(o.b)("li",{parentName:"ol"},"install psiviewer plugin"),Object(o.b)("li",{parentName:"ol"},"configure an IntelliJ Platform SDK"),Object(o.b)("li",{parentName:"ol"},"attach the Community Edition source files to the SDK")))),Object(o.b)("h2",{id:"import"},"Import"),Object(o.b)("ol",null,Object(o.b)("li",{parentName:"ol"},"clone the plugin project"),Object(o.b)("li",{parentName:"ol"},"import project from external model, choose gradle"),Object(o.b)("li",{parentName:"ol"},"select ",Object(o.b)("inlineCode",{parentName:"li"},"use gradle 'wrapper' task configuration")," for easier setup"),Object(o.b)("li",{parentName:"ol"},"Set project sdk to intellij platform sdk")),Object(o.b)("h2",{id:"run"},"Run"),Object(o.b)("p",null,"You can launch a new idea instance with the run ide gradle task"),Object(o.b)("p",null,Object(o.b)("img",{src:n(153).default})))}p.isMDXComponent=!0},96:function(e,t,n){"use strict";n.d(t,"a",(function(){return b})),n.d(t,"b",(function(){return d}));var r=n(0),i=n.n(r);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function l(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function a(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?l(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):l(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function c(e,t){if(null==e)return{};var n,r,i=function(e,t){if(null==e)return{};var n,r,i={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(i[n]=e[n]);return i}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(i[n]=e[n])}return i}var u=i.a.createContext({}),p=function(e){var t=i.a.useContext(u),n=t;return e&&(n="function"==typeof e?e(t):a(a({},t),e)),n},b=function(e){var t=p(e.components);return i.a.createElement(u.Provider,{value:t},e.children)},m={inlineCode:"code",wrapper:function(e){var t=e.children;return i.a.createElement(i.a.Fragment,{},t)}},s=i.a.forwardRef((function(e,t){var n=e.components,r=e.mdxType,o=e.originalType,l=e.parentName,u=c(e,["components","mdxType","originalType","parentName"]),b=p(n),s=r,d=b["".concat(l,".").concat(s)]||b[s]||m[s]||o;return n?i.a.createElement(d,a(a({ref:t},u),{},{components:n})):i.a.createElement(d,a({ref:t},u))}));function d(e,t){var n=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var o=n.length,l=new Array(o);l[0]=s;var a={};for(var c in t)hasOwnProperty.call(t,c)&&(a[c]=t[c]);a.originalType=e,a.mdxType="string"==typeof e?e:r,l[1]=a;for(var u=2;u<o;u++)l[u]=n[u];return i.a.createElement.apply(null,l)}return i.a.createElement.apply(null,n)}s.displayName="MDXCreateElement"}}]);