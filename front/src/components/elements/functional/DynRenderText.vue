<script>
  import TextComponent from "../functional/TextComponent"
  
  export default ({
    
    components: {
      textComponent: TextComponent,
    },
    
    render: function (createElement) {
      let headingId = 'AD-' + this.level;
      let text = this.$slots.default[0].text;
      
      return createElement(
        this.mainElementTag, this.mainElementParams, this.bodyElements
      )
    },
    
    data: function () {
      return {
        mainElementTag: 'p',
        mainElementParams: {
          attrs:{
            name: 'main',
            class: 'text'
          }
        },
        bodyElements: [
          'a',
        {
            elemParams:{
              attrs:{
                name:'link1',
                href:'#/123'
              }
            },
            slots: this.$slots.default
          }
        ]
      }
    },
    
    methods: {
      addLinksToText: function () {
        let result = "<p>" + this.text + "</p>";
        let firstIndex = -1;
        let lastIndex;
        firstIndex = result.indexOf('AD-', firstIndex + 1);
        while (firstIndex !== -1) {
          if ((lastIndex = this.searchTaskLinkEnd(result, firstIndex)) !== -1) {
            result = result.substring(0, firstIndex) + "<a href='/#/task/" + result.substring(firstIndex, lastIndex) + "'>"
              + result.substring(firstIndex, lastIndex)
              + "</a>" + result.substring(lastIndex, result.length);
            firstIndex = result.indexOf('AD-', lastIndex + "<a href='/#/task/'>".length + 3);
          } else {
            firstIndex = result.indexOf('AD-', firstIndex + 3)
          }
        }
        return result;
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
    
    name: "DynRenderText",
    props: {
      level: {
        type: Number,
        required: true
      }
    }
  })
</script>

<style scoped>

</style>
