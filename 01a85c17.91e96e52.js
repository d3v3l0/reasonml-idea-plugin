(window.webpackJsonp=window.webpackJsonp||[]).push([[4,8],{102:function(e,a,t){e.exports={displayOnlyInLargeViewport:"displayOnlyInLargeViewport_37ZF",hideLogoText:"hideLogoText_3e6b",navbarHideable:"navbarHideable_2ENK",navbarHidden:"navbarHidden_7Uo6"}},103:function(e,a,t){e.exports={copyright:"copyright_9pQs"}},110:function(e,a,t){"use strict";var r=t(2),n=t(0),l=t.n(n),i=t(97),c=t(98),o=t(99),s=t(115),m=t(111),d=t(104),b=t(116),u=t(105),v=t(106),f=t(107),g=t(102),h=t.n(g),E=t(112),_="right";a.a=function(){var e,a,t=Object(o.a)(),g=t.siteConfig.themeConfig,p=g.navbar,k=(p=void 0===p?{}:p).title,N=void 0===k?"":k,O=p.items,j=void 0===O?[]:O,y=p.hideOnScroll,w=void 0!==y&&y,L=p.style,C=void 0===L?void 0:L,I=g.colorMode,T=(I=void 0===I?{}:I).disableSwitch,x=void 0!==T&&T,H=t.isClient,M=Object(n.useState)(!1),S=M[0],B=M[1],U=Object(n.useState)(!1),D=U[0],A=U[1],V=Object(d.a)(),J=V.isDarkTheme,K=V.setLightTheme,P=V.setDarkTheme,F=Object(b.a)(w),Q=F.navbarRef,R=F.isNavbarVisible,W=Object(f.a)(),Z=W.logoLink,q=W.logoLinkProps,z=W.logoImageUrl,G=W.logoAlt;Object(u.a)(S);var X=Object(n.useCallback)((function(){B(!0)}),[B]),Y=Object(n.useCallback)((function(){B(!1)}),[B]),$=Object(n.useCallback)((function(e){return e.target.checked?P():K()}),[K,P]),ee=Object(v.a)();Object(n.useEffect)((function(){ee===v.b.desktop&&B(!1)}),[ee]);var ae=function(e){return{leftItems:e.filter((function(e){var a;return"left"===(null!==(a=e.position)&&void 0!==a?a:_)})),rightItems:e.filter((function(e){var a;return"right"===(null!==(a=e.position)&&void 0!==a?a:_)}))}}(j),te=ae.leftItems,re=ae.rightItems;return l.a.createElement("nav",{ref:Q,className:Object(i.a)("navbar","navbar--fixed-top",(e={"navbar--dark":"dark"===C,"navbar--primary":"primary"===C,"navbar-sidebar--show":S},e[h.a.navbarHideable]=w,e[h.a.navbarHidden]=!R,e))},l.a.createElement("div",{className:"navbar__inner"},l.a.createElement("div",{className:"navbar__items"},null!=j&&0!==j.length&&l.a.createElement("div",{"aria-label":"Navigation bar toggle",className:"navbar__toggle",role:"button",tabIndex:0,onClick:X,onKeyDown:X},l.a.createElement("svg",{xmlns:"http://www.w3.org/2000/svg",width:"30",height:"30",viewBox:"0 0 30 30",role:"img",focusable:"false"},l.a.createElement("title",null,"Menu"),l.a.createElement("path",{stroke:"currentColor",strokeLinecap:"round",strokeMiterlimit:"10",strokeWidth:"2",d:"M4 7h22M4 15h22M4 23h22"}))),l.a.createElement(c.a,Object(r.a)({className:"navbar__brand",to:Z},q),null!=N&&l.a.createElement("strong",{className:Object(i.a)("navbar__title",(a={},a[h.a.hideLogoText]=D,a))},N)),te.map((function(e,a){return l.a.createElement(E.a,Object(r.a)({},e,{key:a}))}))),l.a.createElement("div",{className:"navbar__items navbar__items--right"},re.map((function(e,a){return l.a.createElement(E.a,Object(r.a)({},e,{key:a}))})),!x&&l.a.createElement(m.a,{className:h.a.displayOnlyInLargeViewport,"aria-label":"Dark mode toggle",checked:J,onChange:$}),l.a.createElement(s.a,{handleSearchBarToggle:A,isSearchBarExpanded:D}))),l.a.createElement("div",{role:"presentation",className:"navbar-sidebar__backdrop",onClick:Y}),l.a.createElement("div",{className:"navbar-sidebar"},l.a.createElement("div",{className:"navbar-sidebar__brand"},l.a.createElement(c.a,Object(r.a)({className:"navbar__brand",onClick:Y,to:Z},q),null!=z&&l.a.createElement("img",{key:H,className:"navbar__logo",src:z,alt:G}),null!=N&&l.a.createElement("strong",{className:"navbar__title"},N)),!x&&S&&l.a.createElement(m.a,{"aria-label":"Dark mode toggle in sidebar",checked:J,onChange:$})),l.a.createElement("div",{className:"navbar-sidebar__items"},l.a.createElement("div",{className:"menu"},l.a.createElement("ul",{className:"menu__list"},j.map((function(e,a){return l.a.createElement(E.a,Object(r.a)({mobile:!0},e,{onClick:Y,key:a}))})))))))}},113:function(e,a,t){"use strict";var r=t(2),n=t(6),l=t(0),i=t.n(l),c=t(97),o=t(98),s=t(99),m=t(100),d=t(103),b=t.n(d);function u(e){var a=e.to,t=e.href,l=e.label,c=e.prependBaseUrlToHref,s=Object(n.a)(e,["to","href","label","prependBaseUrlToHref"]),d=Object(m.a)(a),b=Object(m.a)(t,{forcePrependBaseUrl:!0});return i.a.createElement(o.a,Object(r.a)({className:"footer__link-item"},t?{target:"_blank",rel:"noopener noreferrer",href:c?b:t}:{to:d},s),l)}var v=function(e){var a=e.url,t=e.alt;return i.a.createElement("img",{className:"footer__logo",alt:t,src:a})};a.a=function(){var e=Object(s.a)().siteConfig,a=(void 0===e?{}:e).themeConfig,t=(void 0===a?{}:a).footer,r=t||{},n=r.copyright,l=r.links,o=void 0===l?[]:l,d=r.logo,f=void 0===d?{}:d,g=Object(m.a)(f.src);return t?i.a.createElement("footer",{className:Object(c.a)("footer",b.a.footer)},i.a.createElement("div",{className:"container"},o&&o.length>0&&i.a.createElement("div",{className:"row footer__links"},o.map((function(e,a){return i.a.createElement("div",{key:a,className:"col footer__col"},null!=e.title?i.a.createElement("h4",{className:"footer__title"},e.title):null,null!=e.items&&Array.isArray(e.items)&&e.items.length>0?i.a.createElement("ul",{className:"footer__items"},e.items.map((function(e,a){return e.html?i.a.createElement("li",{key:a,className:"footer__item",dangerouslySetInnerHTML:{__html:e.html}}):i.a.createElement("li",{key:e.href||e.to,className:"footer__item"},i.a.createElement(u,e))}))):null)}))),(f||n)&&i.a.createElement("div",null,f&&f.src&&i.a.createElement("div",{className:"margin-bottom--sm"},f.href?i.a.createElement("a",{href:f.href,target:"_blank",rel:"noopener noreferrer",className:b.a.footerLogoLink},i.a.createElement(v,{alt:f.alt,url:g})):i.a.createElement(v,{alt:f.alt,url:g})),i.a.createElement("div",{className:Object(c.a)(b.a.copyright),dangerouslySetInnerHTML:{__html:n}})))):null}},118:function(e,a,t){"use strict";t.d(a,"a",(function(){return o}));var r=t(0),n=t.n(r),l=t(98),i=t(47),c=t.n(i);function o(e){var a=e.sidebar;return 0===a.items.length?null:n.a.createElement("div",{className:c.a.sidebar},n.a.createElement("h3",{className:c.a.sidebarItemTitle},a.title),n.a.createElement("ul",{className:c.a.sidebarItemList},a.items.map((function(e){return n.a.createElement("li",{key:e.permalink,className:c.a.sidebarItem},n.a.createElement(l.a,{isNavLink:!0,to:e.permalink,className:c.a.sidebarItemLink,activeClassName:c.a.sidebarItemLinkActive},e.title))}))))}},56:function(e,a,t){"use strict";t.r(a);var r=t(0),n=t.n(r),l=t(109),i=t(98),c=t(118);a.default=function(e){var a=e.tags,t=e.sidebar,r={};Object.keys(a).forEach((function(e){var a=function(e){return e[0].toUpperCase()}(e);r[a]=r[a]||[],r[a].push(e)}));var o=Object.entries(r).sort((function(e,a){var t=e[0],r=a[0];return t===r?0:t>r?1:-1})).map((function(e){var t=e[0],r=e[1];return n.a.createElement("div",{key:t},n.a.createElement("h3",null,t),r.map((function(e){return n.a.createElement(i.a,{className:"padding-right--md",href:a[e].permalink,key:e},a[e].name," (",a[e].count,")")})),n.a.createElement("hr",null))})).filter((function(e){return null!=e}));return n.a.createElement(l.a,{title:"Tags",description:"Blog Tags"},n.a.createElement("div",{className:"container margin-vert--lg"},n.a.createElement("div",{className:"row"},n.a.createElement("div",{className:"col col--2"},n.a.createElement(c.a,{sidebar:t})),n.a.createElement("main",{className:"col col--8"},n.a.createElement("h1",null,"Tags"),n.a.createElement("div",{className:"margin-vert--lg"},o)))))}}}]);