<template>
  <div>
    <p class="text" v-for="txt in addLinksToText()">
      <span>{{txt.text}}</span>
      <router-link class="text__link" v-if="txt.link" :to="{name:'task', params:{key: txt.link}}">{{txt.link}}</router-link>
    </p>
  </div>
</template>

<script>
  export default {
    
    render: function (createElement) {
      return createElement(
        'h' + this.text,   // имя тега
      )
    },
    name: "TextComponent",
    props: {
      text: {
        type: String
      }
    },
    
    methods: {
      addLinksToText: function () {
        let result = this.text;
        let formattedText = [];
        let firstIndex = 0;
        let lastIndex;
        
        if(result === undefined)
          return formattedText;
        
        while((firstIndex = result.indexOf('AD-', firstIndex))!==-1) {
          if ((lastIndex = this.searchTaskLinkEnd(result, firstIndex)) !== -1) {
            formattedText.push({
              text: result.substring(0, firstIndex),
              link: result.substring(firstIndex, lastIndex)
            });
    
            firstIndex = 0;
            result = result.substring(lastIndex, result.length);
          } else {
            firstIndex += 3;
          }
        }
  
  
        formattedText.push({
          text: result,
          link: undefined
        });
        return formattedText;
      },
      
      searchTaskLinkEnd: function (result, index) {
        let lastIndex = index + 3;
        if ('0123456789'.indexOf(result[lastIndex++]) === -1)
          return -1;
        while ('0123456789'.indexOf(result[lastIndex]) !== -1)
          lastIndex++;
        return lastIndex;
      }
    },
    
  }
</script>

<style scoped>

</style>
