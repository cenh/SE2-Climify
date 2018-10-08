<style>
    .view-roomX{
        width: 600px;
        height: 400px;
        padding: 0;
        margin: 0;
        position: fixed;
        top: 50%;
        left: 50%;
        margin-top: -200px;
        margin-left: -300px;
        background-color: #00caf2;
    }

    #temp{
        margin: auto;
    }
    #my_button{
        margin: auto;
    }
</style>

<div class="single-view view-roomX" align="center">
    <form id="temp">
        Value:<br>
        <input type="number" name="value">
    </form>
    <button id="my_button">
        my button
    </button>
</div>
